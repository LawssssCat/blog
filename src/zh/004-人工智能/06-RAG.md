---
title: 检索增强生成（RAG，Retrieval-Augmented Generation）
---

RAG 是构建 “知识库”、“智能客服” 的首选方案。

<!-- more -->

整体流程：

1. 准备部分（提问前）
  1. 分片
    + 产品使用手册
    + 片段列表
  2. 索引
    + Embedding模型：Embedding
    + 向量
    + 向量数据库
2. 回答部分
  1. 召回
    + 用户问题
    + Embedding模型：Embedding
    + 向量
    + 向量数据库：召回
    + 10个相关片段
  2. 重排
    + Cross-Encoder模型：重排
    + 3个相关片段
  3. 生成
    + 大模型（如GPT-4o）：问题+3个相关切片

## 参考

+ 马克的技术工作坊 | RAG 工作机制详解——一个高质量知识库背后的技术全流程 | <https://www.youtube.com/watch?v=WWdlme1EAGI>
+ 马克的技术工作坊 | 使用Python构建RAG系统 —— 用代码还原 RAG系统的每个细节 | <https://www.youtube.com/watch?v=D8mqIMeZ4fQ>
