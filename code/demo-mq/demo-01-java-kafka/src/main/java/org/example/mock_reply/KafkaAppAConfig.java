package org.example.mock_reply;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.CorrelationKey;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class KafkaAppAConfig {
    // 1. 配置用來監聽 B 回覆 (Topic_Response) 的監聽容器
    @Bean
    public ConcurrentMessageListenerContainer<String, String> replyContainer(ConsumerFactory<String, String> cf) {
        ContainerProperties containerProperties = new ContainerProperties(
                "Topic_2_Response"
        );
        // 💥 叢集環境關鍵：每個 A 實例必須有唯一的 Group ID（例如加上動態 UUID）
        // 這樣 B 回傳的訊息才會廣播給所有 A 實例，確保真正發起請求的那台機器能收到回覆
        containerProperties.setGroupId("group_reply_a_" + UUID.randomUUID().toString().substring(0, 8));
        return new ConcurrentMessageListenerContainer<>(cf, containerProperties);
    }

    // 2. 定義 ReplyingKafkaTemplate
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> pf,
            ConcurrentMessageListenerContainer<String, String> replyContainer) {

        ReplyingKafkaTemplate<String, String, String> replyTemplate = new ReplyingKafkaTemplate<>(pf, replyContainer);

        // 可选配置：強制將 Correlation ID 在發送時轉為純文字 String 塞入 Header
        /**
         * 配置前：
         * <code>
         *     CreateTime:1781940518839        kafka_correlationId:�� �]@t�"�;���       null    B_處理完成_[{"action": "create_order", "userId": 123}]
         * </code>
         * 配置后：
         * <code>
         *     CreateTime:1781942527880        kafka_correlationId:40ede50e-a0d4-4e1e-a381-8cb6e181fd82        null    B_處理完成_[{"action": "create_order", "userId": 123}]
         * </code>
         */
//        replyTemplate.setCorrelationIdStrategy(record -> {
//            return new CorrelationKey()
//        });
        replyTemplate.setBinaryCorrelation(false);

        // 設定全域預設超時時間（例如 5 秒）
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(5));
        return replyTemplate;
    }

    @Bean
    @Primary // 💥 關鍵：標記為主要 Bean，當 @Autowired 沒有指定時，預設用這個
    public KafkaTemplate kafkaTemplate(ProducerFactory pf) {
        return new KafkaTemplate<>(pf);
    }
}
