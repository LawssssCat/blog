---
title: llama.cpp
---

模型运行框架

<!-- more -->

+ llama cli —— 命令行方式交互
+ llama server —— web方式交互

```bash
# 通过 open webui 运行
pip install open-webui
open-webui serve
# dockerfile
services:
  open-webui:
    image: ghcr.io/open-webui/open-webui:main
    volumes:
    - ./open-webui:/app/backend/data
  network_mode: "host"
```

## 模型调用方式

### llama.cpp + python 调用

```python
# 库安装
# CMAKE_ARGS="-DGGML_METAL=on" pip install llama-cpp-python
# 库运行（例子）
from llama_cpp import Llama

llm = Llama(
      model_path="./models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf",
      n_gpu_layers=-1, # 取消注释以使用GPU加速
)
output = llm(
      "<｜User｜> 1+1 等于多少？<｜Assistant｜>", # 提示
      max_tokens=512, # 生成最多512个标记，设置为None以生成到上下文窗口的结束
)

print(output)
```

### llama.cpp + CLI/Web 调用

参考： <https://github.com/ggml-org/llama.cpp/tree/master/examples/main>

```yaml
# docker-compose up -d
# 使用docker-compose运行llamacpp
# 也可以本地安装，参考：https://github.com/ggml-org/llama.cpp
services:
  llama:
    image: ghcr.io/ggml-org/llama.cpp:full
    volumes:
      - ./models:/models
    network_mode: "host"
    entrypoint: ["/usr/bin/sleep", "3600"]
    # command: --run -m /models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf  -p "你好，请问ollama是什么？" -n 512 # 运行模型，带参数

# 启动之后测试 llama-cli
# ./llama-cli  -m /models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf
# ./llama-cli  -m /models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf  --threads 16 --prompt '<｜User｜>What is 1+1?<｜Assistant｜>'

# 启动之后测试 ./llama-server -m /models/DeepSeek-R1-Distill-Qwen-1.5B-Q4_K_M.gguf --port 8080
# 基本的网页用户界面可以通过浏览器访问: http://localhost:8080
# 聊天完成端点: http://localhost:8080/v1/chat/completions
```

### llama.cpp + Web + OpenAI 调用

```python
from openai import OpenAI
client = OpenAI(
    base_url="http://localhost:8080",
    api_key="any"  # 当使用本地服务器时，可以设置为任意非空字符串
)
# 发送请求
response_openai = client.chat.completions.create(
    model="DeepSeek-R1-Distill-Qwen",
    messages=[
        {"role": "user", "content": "1+1 等于多少？"}
    ]
)
print(response_openai)
```
