---
title: OpenCode使用笔记
---

官方文档：
<https://opencode.ai/docs>

github:
<https://github.com/anomalyco/opencode>

<!-- more -->

## 安装

```bash
# nodejs
$ node --version
v20.17.0
# opencode
$ npm i -g opencode-ai
# npm config get registry
# npm config set registry https://registry.npmjs.org
# npm install -g cnpm --registry=http://registry.npmmirror.com
# npm config get proxy
# npm config get https-proxy
# npm config set proxy http://your-proxy-server:port
# npm config set https-proxy http://your-proxy-server:port

$ opencode --version
1.15.10
```

```bash
$ opencode
/help
/exit
/models —— 模型列表
/new —— 新建对话
/sessions —— 历史会话
/skills —— 查看skill
```

配置文件： （参考<https://opencodex.cc/posts/opencode-local-models-guide.html>）

```json title="~/.config/opencode/opencode.json"
{
  "$schema": "https://opencode.ai/config.json",
  "provider": {
    "local": { // 自定义的名称
      "npm": "@ai-sdk/openai-compatible", // 指定兼容 OpenAI API 的 SDK。
      "name": "llama-server (local)", // 自定义的名称
      "options": {
        "baseURL": "http://localhost:30268/v1", // 模型 API 地址
        "apiKey": "随便" // 即使本地模型不验证，也需要提供一个值。
      },
      "models": { // 定义可用模型。
        "Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf": { // 本地llama.cpp框架/v1/models接口中的 data.id 值
          "name": "Qwen3.6-35B-A3B (local)",
          "contextWindow": 32768, // 上下文窗口大小。
          "???": ??? // 最大输出 token 数。
        }
      }
    }
  },
  "model": "local/Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf", // 默认使用的模型。
  "enabled_providers": ["local"]
}
```

## 项目

不像openclaw对整机可操作，opencode只对打开的时的当前目录（及其子目录）可操作。
所以，opencode以“项目目录”维度管理配置文件。

```bash
my-project/
├── opencode.json   # OpenCode 配置
├── SOUL.md         # 项目级系统提示词，OpenCode 启动时会自动加载。
├── .gitignore
└── src/
```
