package org.example.kafka.mock_reply.b;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class BusinessV2Consumer {
    @KafkaListener(topics = "Topic_2_Request", groupId = "group_worker_b")
    @SendTo("Topic_2_Response") // 自動將 return 的結果發送到該 Topic
    public String handleRequestFromA(String requestPayload) {
        System.out.println("B 收到 A 的請求資料: " + requestPayload);

        // 執行 B 的後端業務邏輯
        String processedResult = executeBusinessLogic(requestPayload);

        // 直接返回結果即可
        return processedResult;
    }

    private String executeBusinessLogic(String data) {
        return "B_處理完成_[" + data + "]";
    }
}
