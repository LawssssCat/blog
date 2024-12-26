---
title: RASP
date: 2024-12-16
order: 66
---

RASP（Runtime application self-protection，运行时应用程序自我保护） 是一种内置或链接到应用程序环境中的安全技术，与应用程序融为一体，实时监测、阻断攻击，使程序自身拥有自我保护的能力。
RASP 可以增强 WAF 防护工具的安全能力，形成纵深防御的安全防护体系。

> 2014 年 Gartner 安全报告里，RASP 被列为应用安全领域的关键趋势。

那么，RASP 技术是如何应用、优势好处又有哪些呢？

<table>
<thead>
<tr>
<th>能力</th>
<th>SAST（Static Application Security Testing，静态应用安全测试）</th>
<th>DAST（Dynamic Application Security Testing，动态应用安全测试）</th>
<th>IAST（Interactive Application Security Testing，交互式应用安全测试）</th>
<th>RASP（Runtime Application Securtiy Protected，运行时应用程序自我保护）</th>
</tr>
</thead>
<tbody>
<tr>
<td>描述</td>
<td>SAST 是在不运行代码的情况下对源代码或二进制代码进行安全扫描。它如同一位精通语法的校对大师，通过分析代码结构、逻辑和模式，揪出潜在的安全漏洞和不良编码实践。</td>
<td>DAST 是在应用程序运行时，模拟黑客攻击，从外部对系统进行黑盒测试。它就像一位身手敏捷的忍者，从网络层面发起各种攻击，检测应用在实际运行中的安全防御能力。</td>
<td>IAST 是结合了 SAST 和 DAST 的优点，通过在应用程序内部植入探针，在运行时动态地监测和报告安全漏洞。它就像是内嵌的间谍，一边让应用正常运行，一边悄无声息地收集安全情报。</td>
<td>RASP 是一种应用程序保护技术，它在应用程序运行时监视和保护应用程序免受攻击。RASP 使用运行时上下文信息和安全策略来检测和防止攻击，并且可以主动响应和阻止潜在的安全威胁。RASP 可以检测和防御多种攻击向量，包括应用程序层面的漏洞、网络攻击和恶意代码注入等。</td>
</tr>
<tr>
<td>领域</td>
<td>白盒</td>
<td>灰盒</td>
<td>黑盒</td>
<td></td>
</tr>
<tr>
<td>对象</td>
<td>代码</td>
<td>运行中的APP</td>
<td>模拟黑客攻击</td>
<td></td>
</tr>
<tr>
<td>集成阶段</td>
<td>开发</td>
<td>测试</td>
<td>上线</td>
<td></td>
</tr>
<tr>
<td>误报率</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>入侵性</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>产品</td>
<td>商业: Fortify, Checkmarx <br> 开源: Soar, Raptor, RIPS, Seay源码审计系统, VCG, 等</td>
<td>商业: AppScan, AWVS, webinpsect <br> 开源: Owasp ZAP, Xray </td>
<td>商业: Contrast Security, 等  <br> 开源: Xxx RASP </td>
<td>商业: <br> 开源: [btrace](https://github.com/btraceio/btrace)</td>
</tr>
</tbody>
</table>

todo [美团 RASP 大规模研发部署实践总结](https://tech.meituan.com/2024/01/19/runtime-application-self-protection-practice-in-meituan.html)
