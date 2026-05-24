---
title: 本地模型部署
---

Qwen3.6-35B-A3B是开源混合架构模型（不是传统稠密模型）每次激活只需3B（而不是总的35B）参数量同时进入显存。

> 一般35B大模型需要24G才能运行。

llama.cpp的CPU Offload模式通过MoE（混合专家）：

- 由显卡GPU负责注意力层。
- 由内存负责专家层（Expert Layers）

实现CPU加CPU的混合推理。
支持N卡、A卡、I卡和纯CPU运行。

<!-- more-->

## 安装

### 配置

- CPU：[Ryzen 7 7840HS](https://technical.city/zh/cpu/Core-i7-12700-vs-Ryzen-7-7840HS#general-info)
- GPU：[RTX 4090 6GB](https://pc-builds.com/zh/compare/gpu/1751ep/geforce-rtx-3070/geforce-rtx-4090)
- RAM：~~32G × 2~~ 32G
- 系统：Windows 11

### 准备

1. llama.cpp —— 推理框架
    - 下载：<https://github.com/ggml-org/llama.cpp/releases/tag/b9297>
      - [Windows x64 (CUDA 13)](https://github.com/ggml-org/llama.cpp/releases/download/b9297/llama-b9297-bin-win-cuda-13.1-x64.zip)
      - [CUDA 13.1 DLLs](https://github.com/ggml-org/llama.cpp/releases/download/b9297/cudart-llama-bin-win-cuda-13.1-x64.zip) （N卡40系） —— TODO 作用
1. CUDA引擎 —— 使N卡提供推理算力
    - 下载： <https://developer.nvidia.com/cuda-downloads?target_os=Windows&target_arch=x86_64&target_version=11&target_type=exe_local>
      - [CUDA Toolkit 13.2 Update 1](https://developer.download.nvidia.com/compute/cuda/13.2.1/local_installers/cuda_13.2.1_windows.exe)
1. Qwen模型 —— 实现提供推理能力
    - 下载： <https://huggingface.co/unsloth/Qwen3.6-35B-A3B-GGUF/tree/main>
      - [Qwen3.6-35B-A3B-UD-Q4_K_M.gguf （量化规格：Q4_K_M）](https://huggingface.co/unsloth/Qwen3.6-35B-A3B-GGUF/resolve/main/Qwen3.6-35B-A3B-UD-Q4_K_M.gguf?download=true)  —— 主模型（量化规格使用Q4版本可以使速度、显存、效果达到综合较好的平衡点，否则如果使用Q8或FP16精度等满血版8G显存扛不住）
      - [mmproj-BF16.gguf](https://huggingface.co/unsloth/Qwen3.6-35B-A3B-GGUF/resolve/main/mmproj-BF16.gguf?download=true) —— 视觉模型，提供多模态能力
    - 下载： <https://huggingface.co/HauhauCS/Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive> （避免限制）
      - [Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf](https://huggingface.co/HauhauCS/Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive/resolve/main/Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf)
      - [mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf](mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf)

### 目录

```bash
cuda_13.2.1_windows.exe
cudart-llama-bin-win-cuda-13.1-x64.zip
llama-b9297-bin-win-cuda-13.1-x64.zip
llama-b9297-bin-win-cuda-13.1-x64/
llama-b9297-bin-win-cuda-13.1-x64/models/
llama-b9297-bin-win-cuda-13.1-x64/models/mmproj-BF16.gguf
llama-b9297-bin-win-cuda-13.1-x64/models/Qwen3.6-35B-A3B-UD-Q4_K_M.gguf
llama-b9297-bin-win-cuda-13.1-x64/{others...}
```

### 启动命令

::: tabs

@tab 默认

```bat title="start.bat"
@echo off
chcp 65001 >nul
cd llama-b9297-bin-win-cuda-13.1-x64/

@rem "-m —— 主模型"
@rem "--mmproj —— 视觉模型"
@rem "-nql 99 —— 让GPU尽可能参与计算，尽可能把更多的层放到GPU"
@rem "--n-cpu-moe 999 —— 把混合架构的专家模型放到CPU和内存中（即Offload模式：让GPU负责注意力层，内存复制专家层）"
@rem "--flash-attn on —— 降低显存占用，提高模型速度，同时提升上下文长度（尤其是32k、64k的上下文）"
@rem "-c —— 上下文大小"
@rem "-t 12 —— cpu线程数目（不是设置越多越好，设置太多会占用内存宽带，增加CPU调度开销）"
@rem "-b 512 —— 逻辑批处理大小，影响推理吞吐量（即提示词处理速度）"
@rem "--cache-type-k q4_0 —— KV缓存设置"
@rem "--cache-type-v q4_0 —— KV缓存设置"
@rem "--mlock —— 内存锁定，锁定模型到内存大小，防止windows页面交换（否则windows可能把模型转换到硬盘上，硬盘读写速度慢的话可能使模型卡顿）"
llama-server.exe ^
 -m "models\Qwen3.6-35B-A3B-UD-Q4_K_M.gguf" ^
 --n-cpu-moe 45 ^
 --flash-attn on ^
 --jinja ^
 -c 32768 ^
 -t 6 ^
 -b 2048 ^
 -ub 2048 ^
 --cache-type-k q4_0 ^
 --cache-type-v q4_0 ^
 --no-mmap ^
 --host 127.0.0.1 ^
 --port 30268

pause
```

@tab 多模型管理

```bat
@echo off
chcp 65001 >nul
title Qwen3.6-35B-A3B 越狱版

cd /d "%~dp0"

:menu
cls
echo ==========================================
echo.
echo 1. Q4_K_P（4090 推荐）
echo 2. Q4_K_M（稳定版）
echo 3. IQ4_NL（高压缩高质量）
echo 4. IQ2_M（6G/8G 显卡）
echo.
echo ==========================================

set /p choice=请输入数字：

if "%choice%"=="1" (
    llama-server.exe ^
    -m "models\Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-Q4_K_P.gguf" ^
    --mmproj "models\mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf" ^
    -ngl 999 ^
    -c 131072 ^
    -n 8192 ^
    --host 127.0.0.1 ^
    --port 8080
)

if "%choice%"=="2" (
    llama-server.exe ^
    -m "models\Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-Q4_K_M.gguf" ^
    --mmproj "models\mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf" ^
    -ngl 999 ^
    -c 131072 ^
    -n 8192 ^
    --host 127.0.0.1 ^
    --port 8080
)

if "%choice%"=="3" (
    llama-server.exe ^
    -m "models\Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ4_NL.gguf" ^
    --mmproj "models\mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf" ^
    -ngl 999 ^
    -c 131072 ^
    -n 8192 ^
    --host 127.0.0.1 ^
    --port 8080
)

if "%choice%"=="4" (
    llama-server.exe ^
    -m "models\Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf" ^
    --mmproj "models\mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf" ^
    -ngl 999 ^
    -c 8192 ^
    -n 4096 ^
    --host 127.0.0.1 ^
    --port 8080
)

pause
```

@tab 越狱版

```bat
llama-server.exe ^
    -m "models-uncensored\Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf" ^
    --mmproj "models-uncensored\mmproj-Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-f16.gguf" ^
    --no-mmap ^
    --n-cpu-moe 999 ^
    --flash-attn on ^
    --jinja ^
    -t 12 ^
    -c 32768 ^
    -b 4096 ^
    -ub 4096 ^
    --cache-type-k q4_0 ^
    --cache-type-v q4_0 ^
    --mlock ^
    --host 127.0.0.1 ^
    --port 30268 ^
    --temp 0.6 ^
    --top-p 0.95 ^
    --top-k 20 ^
    --min-p 0.00
```

:::

### Agent对接

> llama.cpp api
>
> - `curl http://localhost:30268/v1/models|python -m json.tool`

API base：
<http://127.0.0.1:8080/v1>

API key：
随便或者空

以OpenCode为例：

```json title="~/.config/opencode/opencode.json"
{
  "$schema": "https://opencode.ai/config.json",
  "provider": {
    "local": {
      "npm": "@ai-sdk/openai-compatible",
      "name": "llama-server (local)",
      "options": {
        "baseURL": "http://localhost:30268/v1"
      },
      "models": {
        "Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf": {
          "name": "Qwen3.6-35B-A3B (local)",
          "contextWindow": 32768
        }
      }
    }
  },
  "model": "local/Qwen3.6-35B-A3B-Uncensored-HauhauCS-Aggressive-IQ2_M.gguf",
  "enabled_providers": ["local"]
}
```

## 参考

- 本地8G的GPU跑QWen3.6的35B模型（支持：多模态、超长上下文）  <https://www.youtube.com/watch?v=nU9c-PffHPg> （[link_文章](https://www.freedidi.com/24267.html)）
- 本地6G的GPU跑Qwen3.6“越狱”版模型 <https://www.youtube.com/watch?v=S0_4AUJflNc> （[link_文章](https://www.freedidi.com/24284.html)）

todo N/A/Intel适配
todo 超长上下文

todo llama.cpp
todo 注意力层、专家层
todo KV缓存
