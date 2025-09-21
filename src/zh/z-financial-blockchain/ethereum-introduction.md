---
title: 以太坊介绍
date: 2024-05-09
tag:
  - ethereum
order: 1
---

以太坊（对外宣布的）初衷是搭建一个开源的、全球分布式的计算基础设施。

以太坊本质是一个基于交易的 “状态机”（transaction-based state machine）。

- “状态” —— 执行 “智能合约”（smart contract） 程序的结果，使用区块链来同步和存储系统状态
- “交易” —— 使用名为 “以太币”（ether/ETH） 的加密货币来计量和约束执行资源成本

以太坊平台使开发人员能够构建要给具有内置经济功能的去中心化应用程序（DApp）：在持续自我运行的同上，能减少/消除审计、第三方界面、交易对手风险。

以太坊组成部分：

- **P2P 网络** —— DEVp2p 协议，端口 30303
- **交易（Transaction）** —— 网络消息，包括发送者（sender）、接收者（receiver）、值（value）、数据有效载荷（payload）
- **以太坊虚拟机（EVM）** —— 以太坊状态转换由以太坊虚拟机（EVM）处理，这是一个执行字节码的基于堆栈的虚拟机
- **数据库（Blockchain）** —— 以太坊的区块链作为数据库（通常是 Google 的 LevelDB）存储在每个节点上，包含序列化后的交易和系统状态
- **客户端** —— 比较有名的：Go-Ethereum（Geth）、Parity

以太坊重要概念：

- **账户（Account）** —— 包含地址、余额、随机数、以及可选的存储和代码的对象
  - 普通账户（EOA）：存储和代码均为空
  - 合约账户（Contract）：包含存储和代码
- **地址（Address）** —— 代表一个 EOA/Contract，可以在区块链上接收/发送交易。（准确的说：地址是 ECDSA 公钥的 keccak 散列的最右边的 160 位）
- **交易（Transaction）**
  - 发送以太币和信息
  - 向合约发送的交易可以调用合约代码，并以信息数据为函数参数
  - 向空用户发送信息，可以自动生成以信息作为代码块的合约账户
- **gas** —— 以太坊用于执行智能合约的虚拟燃料。以太坊虚拟机使用核算机制来衡量 gas 的消耗量并限制计算资源的消耗

以太坊 PoW（工作量证明） —— GHOST 幽灵协议

- 挖矿奖励
  - 区块奖励（Block rewards） —— 产生并马上合入新块后，给矿工的奖励。奖励：5/3/2/../0.5/...
  - 叔块奖励（Uncle rewards） —— 产生但延时何如新快后，给矿工的奖励。奖励：7/8 的区块奖励（叔块 “Uncle” 在也称孤块 “orphan”）
  - 叔块引用奖励（Uncle referencing rewards） —— 每引用一个叔块，给矿工的奖励。奖励：1/32 的区块奖励

以太坊 PoS（股权证明） —— Casper 精灵协议

以太坊原理

与比特币区别

客户端的使用

智能合约

Solidity

web3.js

综合运用各种工具

区块链（公链）发展简史：

- 2008 区块链 1.0 —— 比特币：简单记账
- 2014 区块链 2.0 —— 以太坊：智能合约 —— 性能不行：出块时间慢（TPS 低）
- 2017 区块链 3.0 —— EOS/ArcBlock/IOTA：高性能、大吞吐量、开发者友好、用户友好
  - EOS 非去中性化
  - ArcBlock 云节点
  - IOTA 有向无环图

以太坊发展阶段：

- “前沿”（Frontier） `Block #0`
- “家园”（Homestead） `Block #1,150,000`
- “大都会”（Metropolis）

  - 阶段一： “拜占庭”（Byzantium） 2017 年 10 月推出，因为安全问题硬分叉出两个硬分叉（以太坊 ETH、以太坊经典 ETC），这是其中的一个

  > `Block #1,192,000` “The DAO” 合约被攻击

  - 阶段二： “君士坦丁堡”（Constantinople） 2018 年推出，预计包括切换到混合 POW/POS 共识算法

- “宁静”（Serenity）

---

常用工具：

- MetaMask - 浏览器插件钱包
- Remix - 浏览器的 Solidity 在线编辑器
- web3.js - 以太坊 javascript API 库
- Teth - 以太坊客户端（go 语言）
- Ganache - 以太坊客户端（测试环境私链）
- Truffle - 以太坊开发框架

参考：

- 《精通以太坊》（Mastering Ethereum） <https://github.com/ethereumbook/ethereumbook>
- 《以太坊白皮书》（A Next-Generation Smart Contract and Decentralized Application Platform） <https://github.com/ethereum/wiki/wiki/White-Paper>
- 《以太坊黄皮书》（《以太坊：一个安全去中心化的通用交易账本 拜占庭版本》）
- 以太坊官方文档（Ethereum Homestead Documentation） <http://www.ethdocs.org/en/latest/index.html>
- Solidity 官方文档 <https://solidity.readthedocs.io/en/latest/>
