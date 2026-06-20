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
    # 接口1
    curl -X POST http://127.0.0.1:32122/order/process \
      -H "Content-Type: application/json" \
      -d '{"action": "create_order", "userId": 123}'
    # 接口2
    curl -X POST http://127.0.0.1:32122/order/v2/process \
      -H "Content-Type: application/json" \
      -d '{"action": "create_order", "userId": 123}'
    ```
+ A端 = java 客户端
+ B端 = java 客户端

参考：

+ 企業服務總線與整合平台（ESB / iPaaS）
  + 代表產品/技術： 
    + Apache Camel
    + MuleSoft AnyPoint
    + Spring Integration
  + 實務應用：
    + 這些成熟的企業級整合套件中，原生就內建了這種模式（例如 Apache Camel 的 InOut 交換模式）。
      這通常用在「跨企業/跨防火牆」的對接。
      例如外包合作夥伴只提供 HTTP 接口給 A，但 A 與 B 之間因為高規格的金融資安政策，中間只允許開通一條 Kafka 管道通訊，不允許直接打 HTTP 協定。
      此時企業就會利用這套方案，將外部的同步 HTTP 請求，包裝成內部的 Kafka 同步請求。