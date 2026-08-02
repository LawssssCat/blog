---
title: 人工智能笔记
alias:
  - ai
  - artificial intelligence
  - 人工智能
order: 1
---

编年体AI圈大事记：

年份 | 概念 | 说明
--- | --- | ---
1950年 | **NLP（Natural Language Processing，自然语言处理）** | 句子成分拆分
1950年 | AI（Artificial Intelligence，人工智能） | 阿兰·麦席森·图灵（Alan Turing）发表论文《计算机器与智能》，正式提出“图灵测试”，这是 AI 概念的真正起点。
2016年 | AI元年 | 阿法狗围棋打败李世石，次年打败柯洁 （当年人类围棋技术最顶尖的两人）
2017年 | PLM（Pre-trained Language Model，预训练语言模型/小模型） | 指 ELMo、BERT、GPT-1 等早期“小规模”预训练模型。它们证明了“先在海量文本上盲读，再回考场精调（Pre-train + Fine-tune）”的技术路线可行，是 LLM 爆发前的直接孵化器。
2017年 | **LLM（Large Language Model，大语言模型）** | 基于论文《Attention Is All You Need》指出的Transformer架构训练得到的语言模型，本质是基于输入“猜测”下文作为输出。衍生黑话：机器幻觉（Hallucination）、Token
2017年 | AGI（Artificial General Intelligence，通用人工智能 —— 指具有与人类同等或超越人类广泛智能的AI） | 大厂提供的LLM“实现”（打印引号是因为实际效果欠缺，但确实能引起资本幻觉）
2019年⭐ | Multimodality，多模态 | 多种形式数据（如文字、声音、图片、味觉、触觉、...）输入、训练、输出，应用如图片/音频/视频输入和生成。（论文：[CLIP](https://github.com/openai/CLIP)）
2020年⭐ | RAG（Retrieval-Augmented Generation，检索增强生成） | 由FAIR（Facebook AI Research）团队提出，结合信息检索、文本增强、文本生成的NLP技术，将传统信息检索系统的优势与LLM的功能结合在一起，使大模型生成更准确、丰富的文本内容。简称挂知识库。
2023年 | Agent（智能体） | 处理LLM只能处理推断的问题，扩展爬虫、文件处理、系统操作等能力
2023年 | Function Calling | OpenAI推出的工具调用功能
2024年⭐ | MCP（Model Context Protocol，模型上下文协议） | anthropic提出的Agent扩展工具交互规范 （alias: function call， tool calls），统一工具调用规范。
2024年 | 工作流（flow） | 低码工程在风口的再次起飞
2025年 | 小模型 | 开始出现效果能对标大模型的小体量模型，使个人PC部署开始成为可能
2025年⭐ | SKILL | anthropic提出的“问题处理指南”概念，处理大模型已知道问题、信息、可调用工具的情况下仍无法提出高效处理方案的问题。本质上是mcp协议里的一个tool工具，里面分门别类的放置skill名称、描述和预制提示词，大模型根据skill名称、描述判断是否继续接收该skill的预制提示词。 （[link_anthropic_skills](https://github.com/anthropics/skills/tree/main/skills)/[link_skillsmp](https://skillsmp.com/zh)/[link_skillsh](https://skills.sh/)）
2025年 | A2A（Agent to Agent Protocol） | 解决“不同厂商/框架的 Agent 之间如何互相握手、对齐需求、组队协同”的问题
2025年 | Vibe Coding （氛围编程） | 由 Andrej Karpathy（OpenAI的联合创始人、前特斯拉AI负责人） 于2025年2月提出"你只需要完全沉浸在氛围中，甚至忘记代码的存在"，于2025年10月承认"它们的表现完全不够好，整体而言完全'没有帮助'"，并又提出个概念“Agentic Engineering（智能体工程）”。
2026年 | Harness Agent / Agentic Engineering | “Vibe Coding Plus” HA是模型以外的任何东西
2026年⭐ | SubAgent（子智能体） | 在多智能体（Multi-Agent）架构中，由主智能体（Master/Router）根据任务动态派生（Spawn）出的垂直细分智能体。主 Agent 负责分发与统筹，Subagent 负责死磕单一具体任务（如 debug 某段代码、深挖某个搜索），任务结束后销毁，降低主模型的 context 污染。

⭐： 对模型使用有本质提升的概念

<!-- more -->

## 产品/概念

### 算力

计算硬件

+ 英伟达（Nvidia）
  + A100 上市时间：2020 FP16：312TFLOPS FP8：NA 显存：80G 显存带宽：2T/s 互联带宽：600G/s
  + H100 上市时间：2022 FP16：990TFLOPS FP8：1979 显存：80G 显存带宽：3.35T/s 互联带宽：900G/s
  + H200 上市时间：2023 FP16：990TFLOPS FP8：1979 显存：141G 显存带宽：4.8T/s 互联带宽：900G/s
  + H20（中特供） 上市时间：2023 FP16：NA FP8：296
  + B200（爆卖） 上市时间：2024 FP16：2250TFLOPS FP8：4500 显存：180G 显存带宽：7.7T/s 互联带宽：900G/s
+ Cerebras
+ 昇腾
  + 910
  + 950PR 上市时间：2026 = 2.87 * H20

计算框架

+ CUDA
  + TensorRT —— 加速库
  + NCCL —— 多卡通信
+ CANN
  + mindIE —— 加速库
  + HCCL —— 多卡通信
+ Metal
+ HIP
+ CPU + GPU hybrid
+ MindSpore（昇思）
+ ...

### 模型

#### 模型排行榜

+ <https://artificialanalysis.ai/>
+ <https://ollama.com/search>

#### 模型仓库

+ [Hugging Face](https://huggingface.co/)
+ ~~模搭社区~~

#### 模型格式

+ **[GGUF（GPT-Generated Unified Format）](https://github.com/ggml-org/ggml/blob/master/docs/gguf.md)** —— ggml模型二进制格式。通过标准化的键值对和文件结构，使得模型信息的存储和交换更加清晰和高效。注重快速加载、保存和读取模型。
+ ~~GGML~~
+ ~~GGMF~~
+ ~~GGJT~~

#### 模型清单

+ OpenAI(ChatGPT)
  + GPT-5 mini —— OpenAI 擅长逻辑和数学
+ Google
  + Gemini 3 Flash —— Google 擅长图片处理
  + [gemma3](https://ollama.com/library/gemma3) —— Gemini 3 27B 对标 DeepSeek\-R1\-671B
+ 阿里（Alibaba Cloud）
  + Qwen
  + [QwQ](https://ollama.com/library/qwq) —— QwQ\-32B 对标 DeepSeek\-R1\-671B
+ DeepSeek
  + DeepSeek V3.1 —— 中国公司、MoE架构、中文最强、极致便宜
  + DeepSeek\-r1
+ Claude
  + **Claude Opus 4.8** —— 2026年5月发布
  + **Claude Opus 4.6**
  + **Claude Opus 4.5** —— 是 Anthropic 2025 年发布的旗舰通用模型，擅长推理、思考、编码。
  + ~~**Claude Sonnet 4.5** —— 提供了一个理想的性能与成本平衡，适用于中等复杂度的文本生成任务。它在稳定性、响应速度和任务处理能力上表现优异，特别适合那些需要稳定输出的任务，如常规文案创作、博客写作等。~~<span style="color:black;background:black">废话生成器Plus</span>
+ ~~**Claude Haiku 4.5** —— 是最轻量级的模型，适合需要高频率请求和快速响应的场景。它主要面向短文本生成任务和快速问答，成本最低，适合预算有限的小型团队或单一任务需求。~~
+ 其他
  + **GLM-4.7** —— ~~是中国公司 Zhipu AI 发布的大型语言模型，定位为高性能、开源的大模型。~~<span style="color:black;background:black">特点是便宜。</span>
  + MiniMax

![GGML-ollama-cpp-lmstudio-whisper](https://files.catbox.moe/e9fs1w.jpg)

#### 模型训练框架

+ PyTorch —— 一个由Meta（原Facebook）开源机器学习和深度学习框架
+ [ggml（Georgi Gerganov's Machine Learning/GPT-Generated Machine Learning）](https://huggingface.co/blog/zh/introduction-to-ggml) —— 机器学习张量库，专注于在低资源设备上优化推理性能，在推理过程中提供零内存分配的高效内存管理。帮助 llama.cpp 和 whisper.cpp 项目提升模型推理速度和效率。

#### 模型运行框架

+ [whisper.cpp](https://github.com/ggml-org/whisper.cpp) —— [OpenAI的Whisper语音识别模型](https://github.com/openai/whisper)运行框架
+ **[llama.cpp](https://github.com/ggml-org/llama.cpp)** —— 多种通用模型的运行框架
  + 基于这个框架封装的UI
    + ~~LM Studio~~ —— ~~傻瓜~~易上手的界面，但不开源
    + [Ollama](https://github.com/ollama/ollama) —— Docker风格管理模型。使模型与模型仓库之间推送、拉取便捷方便。目标应该是走云+商业化（Docker那套）。
      + open-webui
    + Jan
    + ramalama
    + other UIs
+ ggml-org —— Georgi Gerganov 正在做的其他项目
  + llama.vim
  + llama.vscode

### 接口管理

+ CC Switch —— 切换模型配置、网络调整、使用统计

### Agent

人机信息收集、语言模型交互、操作系统交互

+ Manus
+ ClaudeCode
+ OpenCode
+ OpenClaw
+ [OpenCode](./ai-agent-opencode.md)
+ [Hermes Agent](https://github.com/nousresearch/hermes-agent) —— 会【自动】自己创建一堆skill
+ [lobehub](https://github.com/lobehub/lobehub)

## 开发

### 模型部署

+ [link_模型部署笔记](./ai-model-deployment-local.md)

+ [llama.cpp](./ai-model-runtime-framework-llamacpp.md) —— 模型运行环境
+ [ollama](./ai-model-manage-ollama.md) —— 模型管理工具
+ llama studio
+ open-webui

测试：

+ Zetaphor

### 接口协议：MCP

+ **claude** （see: Claude Protocol Inspector） —— 该协议存在很多问题，但符合工程直觉，在风起飞猪时代用的人多
  + 模型（model）
  + 用户问题（messages.text）
  + 系统提示词（system.text）
  + 工具列表（tool）

## 笑话

### 老虎机

老虎机<br>（Slot Machine） | 氛围编程<br>（Vibe Coding）
--- | ---
买筹码<br>（Buy tokens.） | 买Token<br>（Buy tokens.）
转动老虎机<br>（Pull the lever.） | 写提示词然后点生成<br>（Write a vague prompt and hit "Generate"）
可能中头奖，或者什么都没有<br>（Could be a jackpot, could be nothing.） | 可能得到一个功能正常的应用程序，或者根本无法运行的垃圾<br>（Could be a perfect, bug-free app, could be a hallucinated mess that doesn't even compile.）
闪烁的灯光，诱人的动画<br>（Flashing lights! "BIG WIN!" Jingles!） | 你说的绝对正确！好主意！<br>（"Excellent idea!","Certainly!","Here is the perfect solution for you!"）
我有我自己的策略<br>（"I have a system."） | 我是一名提示词工程师<br>（"I'm a prompt engineer"）
再转一次，我就会把他们都赢回来！<br>（Just one more spin, I can win it all back.） | 再一个提示词，bug就会消失！<br>（"Just one more prompt, it'll fix the bug this time."）
赌场永远盈利<br>（The house always wins） | OpenAI永远盈利<br>（OpenAI always wins.）
过去的4小时去哪了？<br>（"Where did the last 4 hours go?"） | 等等，我刚才花了4小时为我可以再20分钟内写完的函数写提示词吗？<br>（"Wait, I spent 3 hours prompting for a function I could have written in 20 minutes?"）

### 零基础编程

我对编程一窍不通。完全零基础。

我刚刚在30分钟内构建了3个完全功能的网页应用。

```txt
http://localhost:3000/
http://localhost:8000/
http://localhost:5000/
```

## 参考

有用工具：

+ [LLM显存占用计算器](https://watermelonwater.tech/llm)

接口使用相关：

+ 模型接口设计
  + [鲁班大叔_007 | AI到底是如何进行编程的？抓包拆解Claude Code](https://www.bilibili.com/video/BV1AuzkBREhx/)
  + [鲁班大叔_007 | MCP到底是什么？一个视频消除你对MCP最大误解](https://www.bilibili.com/video/BV17kzaBHEoU)
  + [鲁班大叔_007 | SKILL到底有什么用? 抓包拆解SKILL本质？](https://www.bilibili.com/video/BV1DQ6wBoEtN)
+ 接口协议
  todo 从 LLM 到 Agent Skill，一期视频带你打通底层逻辑！ <https://www.bilibili.com/video/BV1E7wtzaEdq/>
  todo MCP为什么要弃用SSE改用Streamable Http？详解MCP3种传输方式 <https://www.bilibili.com/video/BV1Ubu2zHEcB/>
  todo MCP三种通信机制对比:Stdio、SSE、StreamableHTTP <https://www.bilibili.com/video/BV1jRVjzoEu4/>
  todo 7分钟带你了解 SSE，它是什么？ <https://www.bilibili.com/video/BV12auGzHEK2/>

模型工程相关：

+ 模型部署
  + [零度解说 | 本地8G的GPU跑QWen3.6的35B模型（支持：多模态、超长上下文）](https://www.youtube.com/watch?v=nU9c-PffHPg) （[link_文章](https://www.freedidi.com/24267.html)）
  + [零度解说 | 本地6G的GPU跑Qwen3.6“越狱”版模型](https://www.youtube.com/watch?v=S0_4AUJflNc) （[link_文章](https://www.freedidi.com/24284.html)）
+ 部署原理
  + [itsWMW | P1:LLM模型本地部署可行性深度分析：为何现在是关键时刻？](https://www.youtube.com/watch?v=HJpHTWM2BPA) （b站:[link](https://www.bilibili.com/video/BV1CCXWY5EYsa)）
  + [itsWMW | P2:本地LLM技术全景概览：Georgi Gerganov及其开源项目深度解析 (ggml, llama.cpp, ollama)](https://www.youtube.com/watch?v=ZVLT-c3YbXU)
    + 材料：
      + [七千二百袋水泥 | llama.cpp ollama及open-webui的使用介绍](https://watermelonwater.tech/archives/llama.cpp%20ollama%E5%8F%8Aopen-webui%E7%9A%84%E4%BD%BF%E7%94%A8%E4%BB%8B%E7%BB%8D)
  + [itsWMW | P3:GGUF模型格式详解及本地LLM模型下载指南：Hugging Face & ModelScope 模型库实战](https://www.youtube.com/watch?v=TTgqMBL9S3A)
  + [itsWMW | P4:llama.cpp项目深度解读：本地LLM推理引擎核心原理与应用详解](https://www.youtube.com/watch?v=z-nKL2hR3Hs)
  + [itsWMW | P5:llama.cpp实战演示 (llama-cpp-python, llama-cli, llama-server)](https://www.youtube.com/watch?v=7SZar-7PbsY)
  + [itsWMW | P6:Ollama vs llama.cpp：本地LLM部署方案对比及Modelfile自定义模型实战](https://www.youtube.com/watch?v=5MDF6vVsM1I)
  + [itsWMW | P7:Ollama OpenAI API兼容性及Langchain集成指南：本地LLM生态系统构建](https://www.youtube.com/watch?v=P_hvapRZFsA)
  + [itsWMW | P8:OpenWebUI + Ollama实战：打造可视化本地LLM交互界面及基础功能详解](https://www.youtube.com/watch?v=yxyXhybAzU4)

模型制作相关：

todo N/A/Intel适配
todo 超长上下文

todo llama.cpp
todo 注意力层、专家层
todo KV缓存

论文：

+ 重新思考开源生成式人工智能：“开放清洗”与《欧盟人工智能法案》 <https://pure.mpg.de/rest/items/item_3588217_2/component/file_3588218/content>
  + 跟踪指令调优LLM的开放性 <https://opening-up-chatgpt.github.io/>

资讯：

+ AI News Radar —— 整理40多个AI信息源，每30min整理出值得关注的信息。
