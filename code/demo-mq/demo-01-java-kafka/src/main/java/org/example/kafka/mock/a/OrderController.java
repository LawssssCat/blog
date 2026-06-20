package org.example.kafka.mock.a;

import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();

    @PostMapping("/process")
    public DeferredResult<ResponseEntity<String>> handleRequestFromC(@RequestBody String requestData) {
        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>(5000L); // 5秒超時

        // 1. 生成唯一關聯 ID
        String correlationId = UUID.randomUUID().toString();

        // 2. 建立 Future 並存入 Map
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);

        // 3. 封裝訊息並發送至 Kafka (Topic_Request)
        Map<String, Object> message = new HashMap<>();
        message.put("correlationId", correlationId);
        message.put("data", requestData);
        kafkaTemplate.send("Topic_Request", correlationId, JsonUtils.writeValueAsString(message).get());

        // 4. 當 Future 得到結果時，觸發 HTTP 響應返回給 C
        future.thenAccept(result -> {
            deferredResult.setResult(ResponseEntity.ok(result));
        }).exceptionally(ex -> {
            deferredResult.setErrorResult(ResponseEntity.status(504).body("B 處理超時或發生錯誤"));
            return null;
        });

        // 5. 處理超時邏輯
        deferredResult.onTimeout(() -> {
            pendingRequests.remove(correlationId);
            deferredResult.setErrorResult(ResponseEntity.status(504).body("系統超時，B 未能在規定時間內回應"));
        });

        return deferredResult;
    }

    // 6. 監聽 B 回傳的 Topic_Response
    @KafkaListener(topics = "Topic_Response", groupId = "group_a")
    public void listenFromB(ConsumerRecord<String, String> record) {
        record Xx(String correlationId, String result) {
        }
        Xx message = JsonUtils.parseToObject(record.value(), Xx.class).get();
        String correlationId = message.correlationId();
        String resultData = message.result();

        if (correlationId != null) {
            // 從 Map 中取出對應的 Future
            CompletableFuture<String> future = pendingRequests.remove(correlationId);
            if (future != null) {
                // 💥 最核心：注入結果，此時 A 阻塞在 Web 容器的執行緒會被喚醒並返回結果給 C
                future.complete(resultData);
            }
        }
    }
}
