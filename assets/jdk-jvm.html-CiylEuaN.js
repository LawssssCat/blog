import{_ as i,r as l,o as r,c as o,d as s,e as a,b as t,f as e}from"./app-v6wXHRU9.js";const d={},p=s("p",null,"参考：",-1),k=s("ul",null,[s("li",null,"为什么还用 jdk8 https://developer.aliyun.com/article/1108370")],-1),c=s("h2",{id:"openjdk-发展",tabindex:"-1"},[s("a",{class:"header-anchor",href:"#openjdk-发展"},[s("span",null,"OpenJDK 发展")])],-1),h=s("p",null,"历史：",-1),F=s("li",null,"1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。",-1),y={href:"https://github.com/openjdk/jdk",target:"_blank",rel:"noopener noreferrer"},u=s("li",null,"2009 年，甲骨文（Oracle）公司宣布收购 Sun 公司，从此更名为 Oracle JDK。",-1),v=s("li",null,"2019 年 4 月 16 号 Oracle 宣布 JDK 开始商用收费，JDK 从 8u211 版本开始。",-1),C=e(`<blockquote><p>OpenJDK 是由 OpenJDK Community 、Oracle、IBM 领导，连同 Alibaba，Amazon，Ampere，Azul，BellSoft，Canonical，Fujitsu，Google，Huawei，Intel，Java Community，JetBrains，London Java Community，Microsoft，Red Hat，SAP，SouJava，SUSE，Tencent，Twitter ，VMWare 等第三方共同开发、维护的 Java SE 开源参考实现。</p></blockquote><h2 id="openjdk-发行版" tabindex="-1"><a class="header-anchor" href="#openjdk-发行版"><span>OpenJDK 发行版</span></a></h2><p>OpenJDK builds，也叫 OpenJDK 发行版。所有的 JDK 都源自于 OpenJDK。所以严格意义上来说 Oracle JDK 也是 Open JDK 的一个发行版而已。</p><p>不同的是 Oracle JDK 持有 Java 商标，可以使用它以及宣传它，而 OpenJDK 不能使用 Java 商标（关键字）。这一点从 <code>java -version</code> 里能看出来：</p><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># Oracle JDK</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">$</span><span style="color:#032F62;--shiki-dark:#98C379;"> java</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -version</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">java</span><span style="color:#032F62;--shiki-dark:#98C379;"> version</span><span style="color:#032F62;--shiki-dark:#98C379;"> &quot;11.0.20&quot;</span><span style="color:#032F62;--shiki-dark:#98C379;"> 2023-07-18</span><span style="color:#032F62;--shiki-dark:#98C379;"> LTS</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Java(TM</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">) SE Runtime Environment 18.9 (</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">build</span><span style="color:#032F62;--shiki-dark:#98C379;"> 11.0.20+9-LTS-256</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Java</span><span style="color:#032F62;--shiki-dark:#98C379;"> HotSpot</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">(</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">TM</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">) </span><span style="color:#032F62;--shiki-dark:#98C379;">64-Bit</span><span style="color:#032F62;--shiki-dark:#98C379;"> Server</span><span style="color:#032F62;--shiki-dark:#98C379;"> VM</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> 18.9</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (build </span><span style="color:#032F62;--shiki-dark:#98C379;">11.0.20+9-LTS-256,</span><span style="color:#032F62;--shiki-dark:#98C379;"> mixed</span><span style="color:#032F62;--shiki-dark:#98C379;"> mode</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># RedHat OpenJDK</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">$</span><span style="color:#032F62;--shiki-dark:#98C379;"> java</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -version</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">openjdk</span><span style="color:#032F62;--shiki-dark:#98C379;"> version</span><span style="color:#032F62;--shiki-dark:#98C379;"> &quot;11.0.24&quot;</span><span style="color:#032F62;--shiki-dark:#98C379;"> 2024-07-16</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> Runtime</span><span style="color:#032F62;--shiki-dark:#98C379;"> Environment</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (Red_Hat-11.0.24.0.8-2) (</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">build</span><span style="color:#032F62;--shiki-dark:#98C379;"> 11.0.24+8</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> 64-Bit</span><span style="color:#032F62;--shiki-dark:#98C379;"> Server</span><span style="color:#032F62;--shiki-dark:#98C379;"> VM</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (Red_Hat-11.0.24.0.8-2) (</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">build</span><span style="color:#032F62;--shiki-dark:#98C379;"> 11.0.24+8,</span><span style="color:#032F62;--shiki-dark:#98C379;"> mixed</span><span style="color:#032F62;--shiki-dark:#98C379;"> mode,</span><span style="color:#032F62;--shiki-dark:#98C379;"> sharing</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># Ubuntu OpenJDK</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">$</span><span style="color:#032F62;--shiki-dark:#98C379;"> java</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -version</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">openjdk</span><span style="color:#032F62;--shiki-dark:#98C379;"> version</span><span style="color:#032F62;--shiki-dark:#98C379;"> &quot;17.0.12&quot;</span><span style="color:#032F62;--shiki-dark:#98C379;"> 2024-07-16</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> Runtime</span><span style="color:#032F62;--shiki-dark:#98C379;"> Environment</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (build </span><span style="color:#032F62;--shiki-dark:#98C379;">17.0.12+7-Ubuntu-1ubuntu222.04</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> 64-Bit</span><span style="color:#032F62;--shiki-dark:#98C379;"> Server</span><span style="color:#032F62;--shiki-dark:#98C379;"> VM</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (build </span><span style="color:#032F62;--shiki-dark:#98C379;">17.0.12+7-Ubuntu-1ubuntu222.04,</span><span style="color:#032F62;--shiki-dark:#98C379;"> mixed</span><span style="color:#032F62;--shiki-dark:#98C379;"> mode,</span><span style="color:#032F62;--shiki-dark:#98C379;"> sharing</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># Zulu OpenJDK</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">$</span><span style="color:#032F62;--shiki-dark:#98C379;"> java</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -version</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">openjdk</span><span style="color:#032F62;--shiki-dark:#98C379;"> version</span><span style="color:#032F62;--shiki-dark:#98C379;"> &quot;21.0.5&quot;</span><span style="color:#032F62;--shiki-dark:#98C379;"> 2024-10-15</span><span style="color:#032F62;--shiki-dark:#98C379;"> LTS</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> Runtime</span><span style="color:#032F62;--shiki-dark:#98C379;"> Environment</span><span style="color:#032F62;--shiki-dark:#98C379;"> Zulu21.38+21-CA</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (build </span><span style="color:#032F62;--shiki-dark:#98C379;">21.0.5+11-LTS</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> 64-Bit</span><span style="color:#032F62;--shiki-dark:#98C379;"> Server</span><span style="color:#032F62;--shiki-dark:#98C379;"> VM</span><span style="color:#032F62;--shiki-dark:#98C379;"> Zulu21.38+21-CA</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (build </span><span style="color:#032F62;--shiki-dark:#98C379;">21.0.5+11-LTS,</span><span style="color:#032F62;--shiki-dark:#98C379;"> mixed</span><span style="color:#032F62;--shiki-dark:#98C379;"> mode,</span><span style="color:#032F62;--shiki-dark:#98C379;"> sharing</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># Alibaba OpenJDK</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">$</span><span style="color:#032F62;--shiki-dark:#98C379;"> java</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -version</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">openjdk</span><span style="color:#032F62;--shiki-dark:#98C379;"> version</span><span style="color:#032F62;--shiki-dark:#98C379;"> &quot;21.0.4.0.4&quot;</span><span style="color:#032F62;--shiki-dark:#98C379;"> 2024-07-16</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> Runtime</span><span style="color:#032F62;--shiki-dark:#98C379;"> Environment</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (Alibaba </span><span style="color:#032F62;--shiki-dark:#98C379;">Dragonwell</span><span style="color:#032F62;--shiki-dark:#98C379;"> Extended</span><span style="color:#032F62;--shiki-dark:#98C379;"> Edition</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)-21.0.4.0.4+7-GA (</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">build</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> 21.0.4.0.4</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">OpenJDK</span><span style="color:#032F62;--shiki-dark:#98C379;"> 64-Bit</span><span style="color:#032F62;--shiki-dark:#98C379;"> Server</span><span style="color:#032F62;--shiki-dark:#98C379;"> VM</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> (Alibaba </span><span style="color:#032F62;--shiki-dark:#98C379;">Dragonwell</span><span style="color:#032F62;--shiki-dark:#98C379;"> Extended</span><span style="color:#032F62;--shiki-dark:#98C379;"> Edition</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)-21.0.4.0.4+7-GA (</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;">build</span><span style="color:#032F62;--shiki-dark:#98C379;"> 21.0.4.0.4,</span><span style="color:#032F62;--shiki-dark:#98C379;"> mixed</span><span style="color:#032F62;--shiki-dark:#98C379;"> mode,</span><span style="color:#032F62;--shiki-dark:#98C379;"> sharing</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,5),m={href:"https://jcp.org",target:"_blank",rel:"noopener noreferrer"},b=e('<blockquote><p>阿里巴巴入选的 JCP 最高执行委员会，何方神圣？ —— https://mp.weixin.qq.com/s/F4y3GIVSFAVj4EmjAkJ4FQ?spm=a2c6h.12873639.article-detail.22.6b473f045jDP2G</p></blockquote><table><thead><tr><th>发行版</th><th>LTS</th><th>未改上游的构建</th><th>TCK 测试</th><th>宽松式许可证</th><th>商业支持</th></tr></thead><tbody><tr><td>AdoptOpenJDK</td><td>是</td><td>可选</td><td>通过</td><td>是</td><td>可选(IBM)</td></tr><tr><td>Eclipse Temurin</td><td>是</td><td>可选</td><td>通过</td><td>是</td><td>可选(IBM)</td></tr><tr><td>Oracle OpenJDK</td><td>否 ❌</td><td>是 ✅</td><td>通过</td><td>是</td><td>否</td></tr><tr><td>Oracle GraalVM CE</td><td>否 ❌</td><td>否</td><td>通过</td><td>是</td><td>否</td></tr><tr><td>Oracle Java SE</td><td>是</td><td>否</td><td>通过</td><td>否 ❌</td><td>是 ✅</td></tr><tr><td>IBM Java SDK</td><td>是</td><td>否</td><td>通过</td><td>否 ❌</td><td>是 ✅</td></tr><tr><td>Red Hat OpenJDK</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>是 ✅</td></tr><tr><td>Linux 捆绑发行版</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>否</td></tr><tr><td>Azul Zulu</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>可选</td></tr><tr><td>Amazon Corretto</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>可选(AWS)</td></tr><tr><td>Alibaba Dragonwell</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>否</td></tr><tr><td>Tencent Kona</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>否</td></tr><tr><td>Huawei Bisheng</td><td>是</td><td>否</td><td>通过</td><td>是</td><td>否</td></tr></tbody></table><h3 id="todo-对比各发新版" tabindex="-1"><a class="header-anchor" href="#todo-对比各发新版"><span>todo 对比各发新版</span></a></h3><p>https://developer.aliyun.com/article/1108370</p>',4);function B(A,J){const n=l("ExternalLinkIcon");return r(),o("div",null,[p,k,c,h,s("ul",null,[F,s("li",null,[a("2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月 15 日正式发布 "),s("a",y,[a("OpenJDK"),t(n)]),a("。")]),u,v]),C,s("p",null,[a("为了保证多种 OpenJDK 发行版是 “靠谱” 的，"),s("a",m,[a("JCP（Java Community Process）"),t(n)]),a(" 规定发行版发布前需要通过 TCK 兼容性测试来认证该发行版是 “靠谱” 的。")]),b])}const D=i(d,[["render",B],["__file","jdk-jvm.html.vue"]]),K=JSON.parse('{"path":"/zh/dev-java-commonsense/jdk-jvm.html","title":"JDK 介绍","lang":"zh-CN","frontmatter":{"title":"JDK 介绍","date":"2024-10-24T00:00:00.000Z","tag":["java"],"order":1,"description":"参考： 为什么还用 jdk8 https://developer.aliyun.com/article/1108370 OpenJDK 发展 历史： 1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。 2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月...","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/dev-java-commonsense/jdk-jvm.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"JDK 介绍"}],["meta",{"property":"og:description","content":"参考： 为什么还用 jdk8 https://developer.aliyun.com/article/1108370 OpenJDK 发展 历史： 1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。 2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月..."}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-10-20T15:15:52.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"java"}],["meta",{"property":"article:published_time","content":"2024-10-24T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-10-20T15:15:52.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"JDK 介绍\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-10-24T00:00:00.000Z\\",\\"dateModified\\":\\"2024-10-20T15:15:52.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"OpenJDK 发展","slug":"openjdk-发展","link":"#openjdk-发展","children":[]},{"level":2,"title":"OpenJDK 发行版","slug":"openjdk-发行版","link":"#openjdk-发行版","children":[{"level":3,"title":"todo 对比各发新版","slug":"todo-对比各发新版","link":"#todo-对比各发新版","children":[]}]}],"git":{"createdTime":1729415191000,"updatedTime":1729437352000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":2}]},"readingTime":{"minutes":2.25,"words":676},"filePathRelative":"zh/dev-java-commonsense/jdk-jvm.md","localizedDate":"2024年10月24日","excerpt":"<p>参考：</p>\\n<ul>\\n<li>为什么还用 jdk8 https://developer.aliyun.com/article/1108370</li>\\n</ul>\\n<h2>OpenJDK 发展</h2>\\n<p>历史：</p>\\n<ul>\\n<li>1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。</li>\\n<li>2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月 15 日正式发布 <a href=\\"https://github.com/openjdk/jdk\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">OpenJDK</a>。</li>\\n<li>2009 年，甲骨文（Oracle）公司宣布收购 Sun 公司，从此更名为 Oracle JDK。</li>\\n<li>2019 年 4 月 16 号 Oracle 宣布 JDK 开始商用收费，JDK 从 8u211 版本开始。</li>\\n</ul>","autoDesc":true}');export{D as comp,K as data};