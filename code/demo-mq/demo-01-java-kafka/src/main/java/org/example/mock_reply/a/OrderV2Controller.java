package org.example.mock_reply.a;

import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/order/v2")
public class OrderV2Controller {
    @Resource
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

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
