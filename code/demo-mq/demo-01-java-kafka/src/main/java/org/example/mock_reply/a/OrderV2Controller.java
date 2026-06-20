package org.example.mock_reply.a;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/order/v2")
public class OrderV2Controller {
    // 宣告為成員變數
//    @Resource(name = "orderReplyingKafkaTemplate")
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    // 透過構造函數注入基礎的 Factory
    public OrderV2Controller(ProducerFactory<String, String> pf, ConsumerFactory<String, String> cf) {

        // 1. 建立監聽容器
        ContainerProperties containerProperties = new ContainerProperties("Topic_2_Response");
        containerProperties.setGroupId("group_order_reply_" + UUID.randomUUID().toString().substring(0, 8));
        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(cf, containerProperties);

        // 2. new 出 Template
        this.replyingKafkaTemplate = new ReplyingKafkaTemplate<>(pf, container);
        this.replyingKafkaTemplate.setBinaryCorrelation(false);
        this.replyingKafkaTemplate.setDefaultReplyTimeout(Duration.ofSeconds(5));

        // 💥 核心關鍵：因為不是 Bean，Spring 啟動時不會理它。
        // 你必須手動呼叫 start()，它才會開始監聽 Kafka 的回覆 Topic！
        this.replyingKafkaTemplate.start();
    }


    // 💡 良好的習慣：在 Service 銷毀時手動關閉，釋放 Kafka 連線資源
    @PreDestroy
    public void destroy() {
        if (this.replyingKafkaTemplate != null) {
            this.replyingKafkaTemplate.stop();
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> callBViaKafka(@RequestBody String requestData) {
        // 建立標準的 Kafka ProducerRecord
        ProducerRecord<String, String> record = new ProducerRecord<>("Topic_2_Request", requestData);

        // 呼叫 sendAndReceive，這會自動在 Header 帶入 correlationId
        RequestReplyFuture<String, String, String> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);

        try {
            // 💥 同步阻塞等待 B 的回覆，最多等 5 秒
            ConsumerRecord<String, String> consumerRecord = sendAndReceive.get(5, TimeUnit.SECONDS);

            // 成功拿到 B 的結果，返回給 C
            return ResponseEntity.ok("收到 B 處理結果: " + consumerRecord.value());

        } catch (TimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("錯誤：後端 B 處理超時");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系統異常: " + e.getMessage());
        }
    }
}
