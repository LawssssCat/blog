# demo

场景：

+ 对外提供Web接口
+ 内部存在消息队列通信

```text
 C 端          A 端 (Web / Producer / Consumer)           B 端 (Consumer / Producer)

  |                |                                          |
  |--- 1. 調用 --->|                                          |
  |    (HTTP/RPC)  |--- 2. 生成 correlationId                 |
  |                |    3. 儲存 Future 到記憶體 Map            |
  |                |--- 4. 發送請求訊息 (帶上 ID) ----------->| (監聽 Topic_Request)
  |                |                                          | 5. 執行業務邏輯...
  |                |                                          | 6. 處理完成
  |                |                                          |--- 7. 發送回覆訊息 (帶上同個 ID) --->
  |                |<-- 8. 監聽並接收到回覆訊息 ----------------| (監聽 Topic_Response)
  |                | 9. 從 Map 找出對應的 Future              |
  |                | 10. 喚醒等待中的執行緒並注入結果         |
  |<-- 11. 返回 ---|                                          |
```

模拟：

+ C端
    ```text
    curl -X POST http://127.0.0.1:32122/order/process \
         -H "Content-Type: application/json" \
         -d '{"action": "create_order", "userId": 123}'
    ```
+ A端 = java 客户端
+ B端 = java 客户端
