# 说明

界面：
- http://localhost:8760/zipkin/

## 功能：持久化

可以存数据库或者es。

存ES（elasticsearch 6.6.0）

```bash
<dependency>
    <groupId>io.zipkin.java</groupId>
    <artifactId>zipkin-autoconfigure-storage-elasticsearch-http</artifactId>
    <version>2.8.4</version>
</dependency>

zipkin.storage.type=elasticsearch
zipkin.storage.elasticsearch.cluster=elasticsearch
zipkin.storage.elasticsearch.host=http://localhost:9201
zipkin.storage.elasticsearch.index=zipkin
```
