---
title: ollama
---

用 ollama 下载、推送：

```bash
ollama pull example # 拉取一个模型
ollama rm example   # 删除一个模型
ollama run example  # 运行模型
ollama stop example # 停止运行中的模型
ollama ps           # 列出当前加载的模型
ollama show example # 显示模型信息
ollama list         # 列出本地已有的模型
ollama cp example my-model # 复制一个模型
ollama serve        # 在不启动桌面应用的情况下，启动 Ollama 服务器
ollama create mymodel -f ./Modelfile # 创建一个模型
```

<!-- more -->

## Modelfile

如何使用Modelfile： <https://github.com/ollama/ollama/blob/main/docs/modelfile.md>

```bash
FROM deepseek-r1:1.5b
# FROM qwen2.5:1.5b
# FROM ./models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf
SYSTEM """
你是由哇喂技术有限公司(WaWei Technologies Co., Ltd.)开发的大语言模型,简称哇喂(WaWei)。
你的名字是“摇摇”(Yao Yao)，你就是“摇摇”，不是 Qwen。
You are a large language model developed by WaWei Technologies Co., Ltd. (哇喂技术有限公司), also known as WaWei.
Your name is “摇摇”(Yao Yao)，你就是“摇摇”, not Qwen.
"""
PARAMETER temperature 0.6
```

```bash
ollama create yylx -f Modelfile
```

## 对 openai-API 的支持

参考：
<https://github.com/ollama/ollama/blob/main/docs/openai.md>

```python
from openai import OpenAI

client = OpenAI(
  base_url='http://localhost:11434/v1/',

  # required but ignored
  api_key='ollama',
)

completion = client.chat.completions.create(
  model="deepseek-r1:1.5b",
  messages=[{
    "role": "user",
    "content": "你好,1+1等于多少?"
  }]
)
print(completion)

```

## 对 langchain 的支持

参考：
<https://python.langchain.com/docs/integrations/chat/ollama/>

```python
# https://python.langchain.com/docs/integrations/chat/ollama/
from langchain_ollama import ChatOllama

llm = ChatOllama(model="deepseek-r1:1.5b")

messages = [
  ("human", "你好,1+1等于多少?"),
]

response = llm.invoke(messages)
print(response)
```
