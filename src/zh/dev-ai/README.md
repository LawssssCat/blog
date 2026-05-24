---
title: 人工智能笔记
alias:
  - ai
  - artificial intelligence
  - 人工智能
order: 1
---

<!-- more -->

## 概念

年份 | 概念 | 说明
--- | --- | ---
1950年 | NLP（Natural Language Processing，自然语言处理） | 句子成分拆分
1950年 | AI（Artificial Intelligence，人工智能） | 无
2016年 | AI元年 | 阿法狗围棋打败李世石，次年打败柯洁 （当年人类围棋技术最顶尖的两人）
2017年 | AGI（Artificial General Intelligence，通用人工智能） | 大厂提供的LLM实现
2017年 | LLM（Large Language Model，大语言模型） | 指由Transformer架构训练得到的语言模型，本质是基于输入“猜测”下文作为输出。衍生黑化：机器幻觉（Hallucination）、Token
2019年 | Multimodality，多模态 | 多种形式数据（如文字、声音、图片、味觉、触觉、...）输入、训练、输出，应用如图片/音频/视频输入和生成。（论文：[CLIP](https://github.com/openai/CLIP)）
2020年 | RAG（Retrieval-Augmented Generation，检索增强生成） | 由FAIR（Facebook AI Research）团队提出，结合信息检索、文本增强、文本生成的NLP技术，将传统信息检索系统的优势与LLM的功能结合在一起，使大模型生成更准确、丰富的文本内容。简称挂知识库。
2023年 | Agent（智能体） | 处理LLM只能处理推断的问题，扩展爬虫、文件处理、系统操作等能力
2024年 | MCP（Model Context Protocol，模型上下文协议） | anthropic提出的Agent扩展工具交互规范 （alias: function call， tool calls）。有了交互规范，则可作为单独服务对接不同Agent供应商，Agent供应商只需考虑提示词的书写。
2024年 | 工作流（flow） | 低码工程在风口的再次起飞
2025年 | SKILL | anthropic提出的“问题处理指南”概念，处理大模型已知道问题、信息、可调用工具的情况下仍无法提出高效处理方案的问题。本质上是mcp协议里的一个tool工具，里面分门别类的放置skill名称、描述和预制提示词，大模型根据skill名称、描述判断是否继续接收该skill的预制提示词。 （[link_anthropic_skills](https://github.com/anthropics/skills/tree/main/skills)/[link_skillsmp](https://skillsmp.com/zh)/[link_skillsh](https://skills.sh/)）
2025年 | Vibe Coding （氛围编程） | 由 Andrej Karpathy（OpenAI的联合创始人、前特斯拉AI负责人） 于2025年2月提出"你只需要完全沉浸在氛围中，甚至忘记代码的存在"，于2025年10月承认"它们的表现完全不够好，整体而言完全'没有帮助'"。（todo 观点是什么？）
2026年 | Harness Agent | “Vibe Coding Plus” HA是模型以外的任何东西
2026年 | A2A（Agent to Agent） | todo

## 产品

### 算力

GPU

+ 英伟达（Nvidia）
+ Cerebras

### 模型

全球模型排行榜：

+ <https://artificialanalysis.ai/>

---

+ GPT-5 mini —— OpenAI 擅长逻辑和数学
+ Gemini 3 Flash —— Google 擅长图片处理
+ Qwen
+ DeepSeek V3.1 —— 中国公司、MoE架构、中文最强、极致便宜
+ **GLM-4.7** —— ~~是中国公司 Zhipu AI 发布的大型语言模型，定位为高性能、开源的大模型。~~<span style="color:black;background:black">特点是便宜。</span>
+ **Claude Opus 4.5** —— 是 Anthropic 2025 年发布的旗舰通用模型，擅长推理、思考、编码。
+ ~~**Claude Sonnet 4.5** —— 提供了一个理想的性能与成本平衡，适用于中等复杂度的文本生成任务。它在稳定性、响应速度和任务处理能力上表现优异，特别适合那些需要稳定输出的任务，如常规文案创作、博客写作等。~~<span style="color:black;background:black">废话生成器Plus</span>
+ ~~**Claude Haiku 4.5** —— 是最轻量级的模型，适合需要高频率请求和快速响应的场景。它主要面向短文本生成任务和快速问答，成本最低，适合预算有限的小型团队或单一任务需求。~~
+ MiniMax

### 模型管理

+ CC Switch —— 切换模型配置、网络调整、使用统计

### Agent

人机信息收集、语言模型交互、操作系统交互

+ Manus
+ Claude Code
+ Open Claw
+ [OpenCode](./ai-agent-opencode.md)
+ [Hermes Agent](https://github.com/nousresearch/hermes-agent) —— 会【自动】自己创建一堆skill

## 协议

> 参考：
>
> + AI到底是如何进行编程的？抓包拆解Claude Code by 鲁班大叔_007 - <https://www.bilibili.com/video/BV1AuzkBREhx/>
> + MCP到底是什么？一个视频消除你对MCP最大误解 by 鲁班大叔_007 - <https://www.bilibili.com/video/BV17kzaBHEoU>
> + SKILL到底有什么用? 抓包拆解SKILL本质？ by 鲁班大叔_007 - <https://www.bilibili.com/video/BV1DQ6wBoEtN>

### MCP

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

http://localhost:3000/
http://localhost:8000/
http://localhost:5000/
