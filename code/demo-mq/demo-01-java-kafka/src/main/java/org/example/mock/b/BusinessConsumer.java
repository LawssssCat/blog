package org.example.mock.b;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.utils.JsonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A 服務多實例部署（叢集模式）的問題：
 * 痛點：
 * C 的請求打到 A-實例1，A-實例1 把訊息發給了 B。
 * B 處理完發回 Topic_Response，但這個回覆訊息卻被 A-實例2 消費到了。
 * 這時 A-實例2 的 Map 裡根本沒有這個 correlationId，
 * 而 A-實例1 則會一直等到超時。
 * 解法：
 * 對於 Topic_Response，A 的每個實例必須擁有獨立的 Consumer Group（例如用 group_a_${instance_ip} 或 UUID）。
 * 這樣 B 回傳的訊息會廣播給 A 的所有人，只有真正持有該 correlationId 的那台 A 實例會去處理它，其他人直接忽略。
 */
@Component
@Slf4j
public class BusinessConsumer {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "Topic_Request", groupId = "group_b")
    public void handleRequest(ConsumerRecord<String, String> record) {
        record Xx(String correlationId, String data) {
        }
        Xx requestMessage = JsonUtils.parseToObject(record.value(), Xx.class).get();
        String correlationId = requestMessage.correlationId();
        String data = requestMessage.data();

        // 1. 執行 B 的業務邏輯
        String result = executeBusinessLogic(data);

        // 2. 組裝回覆訊息
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("correlationId", correlationId); // 必須原樣帶回
        responseMessage.put("result", result);

        // 3. 發送回 A 監聽的 Topic
        kafkaTemplate.send("Topic_Response", correlationId, JsonUtils.writeValueAsString(responseMessage).get());
    }

    private String executeBusinessLogic(String data) {
        // 模擬耗時業務處理
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        return "B 成功處理完資料: " + data;
    }
}
