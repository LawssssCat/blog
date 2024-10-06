import{_ as r,r as i,o,c as p,d as s,e,b as n,f as l}from"./app-B9PSY2ES.js";const c={},h=s("h2",{id:"switch",tabindex:"-1"},[s("a",{class:"header-anchor",href:"#switch"},[s("span",null,"Switch")])],-1),d={href:"https://switch.hacks.guide/",target:"_blank",rel:"noopener noreferrer"},k={href:"https://switch.hacks.guide/",target:"_blank",rel:"noopener noreferrer"},u={href:"https://github.com/CTCaer/hekate",target:"_blank",rel:"noopener noreferrer"},m=s("ul",null,[s("li",null,[e('Options/Auto Boot/ON —— 自动进入预设系统 （开机时，长按 "'),s("code",null,"-"),e('" 进入引导系统）')])],-1),y={href:"https://github.com/Atmosphere-NX/Atmosphere",target:"_blank",rel:"noopener noreferrer"},b=s("p",null,"整合包 （hekate + atmosphere + 自制软件）",-1),F=s("li",null,[s("s",null,"https://www.sdsetup.com/?p=/console&q=switch —— 自定义整合包"),e(" （已死）")],-1),f={href:"https://github.com/Team-Neptune/DeepSea",target:"_blank",rel:"noopener noreferrer"},g=s("ul",null,[s("li",null,[e("用法：解压放入 TF 卡根目录，并将 "),s("code",null,"hekate_ctcaer_x.x.x.bin"),e(" 更名成 "),s("code",null,"payload.bin")])],-1),v={href:"https://github.com/sskyNS/RoastDuck-CFWPack",target:"_blank",rel:"noopener noreferrer"},_={href:"https://github.com/xingshijie/ShallowSea",target:"_blank",rel:"noopener noreferrer"},C=l(`<p>系统固件</p><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 系统版本： 新游戏一般需要同时期新系统</span></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 真实系统可在线升级/离线升级 【大版本只能升不能降！！！】</span></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 虚拟系统只能离线升级 【随便玩，一般只在虚拟系统升级系统】</span></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 固件：</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">12.1.0</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> #</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">15.0.1</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> #</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">18</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 24</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">19</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 24</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,2),w={href:"https://github.com/THZoria/NX_Firmware",target:"_blank",rel:"noopener noreferrer"},A=s("p",null,"大佬",-1),E={href:"https://www.youtube.com/@carcaschoi",target:"_blank",rel:"noopener noreferrer"},D={href:"https://space.bilibili.com/36010",target:"_blank",rel:"noopener noreferrer"},S={href:"https://www.marsshen.com/posts/20e16ead/",target:"_blank",rel:"noopener noreferrer"},x=l(`<div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 整合包目录</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">emuMMC</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 虚拟系统（Custom</span><span style="color:#032F62;--shiki-dark:#98C379;"> FirmWare，CFW）。</span><span style="color:#032F62;--shiki-dark:#98C379;"> （sysMMC</span><span style="color:#032F62;--shiki-dark:#98C379;"> =</span><span style="color:#032F62;--shiki-dark:#98C379;"> 真实系统）</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Nintendo</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 正版系统文件（运行时生成的上下文，最好保留）</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">switch</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 存放自制插件</span></span>
<span class="line"><span style="color:#005CC5;--shiki-dark:#56B6C2;">...</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 常用工具</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Daybreak</span><span style="color:#032F62;--shiki-dark:#98C379;"> （破晓）</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 升级固件</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">All-in-One</span><span style="color:#032F62;--shiki-dark:#98C379;"> Switch</span><span style="color:#032F62;--shiki-dark:#98C379;"> Updater</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">EdiZon</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 作弊工具</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">sys-clk</span><span style="color:#032F62;--shiki-dark:#98C379;"> manager</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 超频</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">DBI</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 安装/文件管理</span><span style="color:#032F62;--shiki-dark:#98C379;"> （不开源）</span><span style="color:#032F62;--shiki-dark:#98C379;">  https://github.com/rashevskyv/dbi</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Awoo</span><span style="color:#032F62;--shiki-dark:#98C379;"> Installer</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 安装</span><span style="color:#032F62;--shiki-dark:#98C379;"> https://github.com/Huntereb/Awoo-Installer</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">DeepSea</span><span style="color:#032F62;--shiki-dark:#98C379;"> Toolbox</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 后台管理</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">Tesla</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>安装包</p><ul><li>nsp —— nsp=数字版（需要安装，安装完后安装包+游戏本体要两倍空间）</li><li>xci —— 卡带版（无需安装，换游戏需要切换卡带）（实际安装一样需要两倍空间）</li><li>nsz</li><li>xcz</li></ul><p>游戏</p><ul><li>塞尔达系列 <ul><li>塞尔达传说：智慧的再现 （The Legend of Zelda Echoes of Wisdom）</li></ul></li></ul>`,6);function N(T,M){const a=i("ExternalLinkIcon"),t=i("RepoLink");return o(),p("div",null,[h,s("p",null,[e("系统 （"),s("a",d,[e("入门"),n(a)]),e("、"),s("a",k,[e("源起"),n(a)]),e("）")]),s("ul",null,[s("li",null,[s("p",null,[s("a",u,[e("hekate"),n(a)]),e(" —— 启动引导")]),m]),s("li",null,[s("p",null,[s("a",y,[e("atmosphere"),n(a)]),e(" （ASM） —— 虚拟固件")])]),s("li",null,[b,s("ul",null,[F,s("li",null,[s("a",f,[e("DeepSea"),n(a)]),e(" —— Neptune 团队 "),g]),s("li",null,[s("a",v,[e("sskyNS/RoastDuck-CFWPack"),n(a)]),e(" —— 为中国宝宝定制")]),s("li",null,[s("s",null,[s("a",_,[e("carcaschoi/ShallowSea"),n(a)]),e(" —— Repository unavailable due to DMCA takedown.")])])])]),s("li",null,[C,s("ul",null,[s("li",null,[s("a",w,[e("THZoria/NX_Firmware"),n(a)]),e(" —— 离线固件下载")])])]),s("li",null,[A,s("ul",null,[s("li",null,[s("a",E,[e("carcaschoi"),n(a)])]),s("li",null,[s("a",D,[e("illyasever"),n(a)])]),s("li",null,[e("参考： "),s("ul",null,[s("li",null,[s("a",S,[e("https://www.marsshen.com/posts/20e16ead/"),n(a)])]),s("li",null,[n(t,{path:"/archive/RDPv8.8.1烤鸭包使用教程.pptx"})])])])])])]),x])}const R=r(c,[["render",N],["__file","func-game.html.vue"]]),z=JSON.parse('{"path":"/zh/posts/func-game.html","title":"游戏","lang":"zh-CN","frontmatter":{"title":"游戏","tag":["life"],"description":"Switch 系统 （入门、源起） hekate —— 启动引导 Options/Auto Boot/ON —— 自动进入预设系统 （开机时，长按 \\"-\\" 进入引导系统） atmosphere （ASM） —— 虚拟固件 整合包 （hekate + atmosphere + 自制软件） （已死） DeepSea —— Neptune 团队 用法：解压放...","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/posts/func-game.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"游戏"}],["meta",{"property":"og:description","content":"Switch 系统 （入门、源起） hekate —— 启动引导 Options/Auto Boot/ON —— 自动进入预设系统 （开机时，长按 \\"-\\" 进入引导系统） atmosphere （ASM） —— 虚拟固件 整合包 （hekate + atmosphere + 自制软件） （已死） DeepSea —— Neptune 团队 用法：解压放..."}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-10-02T01:32:20.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"life"}],["meta",{"property":"article:modified_time","content":"2024-10-02T01:32:20.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"游戏\\",\\"image\\":[\\"\\"],\\"dateModified\\":\\"2024-10-02T01:32:20.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"Switch","slug":"switch","link":"#switch","children":[]}],"git":{"createdTime":1727832740000,"updatedTime":1727832740000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":1}]},"readingTime":{"minutes":1.39,"words":417},"filePathRelative":"zh/posts/func-game.md","localizedDate":"2024年10月2日","excerpt":"<h2>Switch</h2>\\n<p>系统 （<a href=\\"https://switch.hacks.guide/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">入门</a>、<a href=\\"https://switch.hacks.guide/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">源起</a>）</p>\\n<ul>\\n<li>\\n<p><a href=\\"https://github.com/CTCaer/hekate\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">hekate</a> —— 启动引导</p>\\n<ul>\\n<li>Options/Auto Boot/ON —— 自动进入预设系统 （开机时，长按 \\"<code>-</code>\\" 进入引导系统）</li>\\n</ul>\\n</li>\\n<li>\\n<p><a href=\\"https://github.com/Atmosphere-NX/Atmosphere\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">atmosphere</a> （ASM） —— 虚拟固件</p>\\n</li>\\n<li>\\n<p>整合包 （hekate + atmosphere + 自制软件）</p>\\n<ul>\\n<li><s>https://www.sdsetup.com/?p=/console&amp;q=switch —— 自定义整合包</s> （已死）</li>\\n<li><a href=\\"https://github.com/Team-Neptune/DeepSea\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">DeepSea</a> —— Neptune 团队\\n<ul>\\n<li>用法：解压放入 TF 卡根目录，并将 <code>hekate_ctcaer_x.x.x.bin</code> 更名成 <code>payload.bin</code></li>\\n</ul>\\n</li>\\n<li><a href=\\"https://github.com/sskyNS/RoastDuck-CFWPack\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">sskyNS/RoastDuck-CFWPack</a> —— 为中国宝宝定制</li>\\n<li><s><a href=\\"https://github.com/xingshijie/ShallowSea\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">carcaschoi/ShallowSea</a> —— Repository unavailable due to DMCA takedown.</s></li>\\n</ul>\\n</li>\\n<li>\\n<p>系统固件</p>\\n<div class=\\"language-bash line-numbers-mode\\" data-ext=\\"sh\\" data-title=\\"sh\\"><pre class=\\"shiki shiki-themes github-light one-dark-pro\\" style=\\"background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf\\" tabindex=\\"0\\"><code><span class=\\"line\\"><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"># 系统版本： 新游戏一般需要同时期新系统</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"># 真实系统可在线升级/离线升级 【大版本只能升不能降！！！】</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"># 虚拟系统只能离线升级 【随便玩，一般只在虚拟系统升级系统】</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"># 固件：</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">12.1.0</span><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"> #</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">15.0.1</span><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"> #</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">18</span><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"> # 24</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">19</span><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"> # 24</span></span>\\n<span class=\\"line\\"></span></code></pre><div class=\\"line-numbers\\" aria-hidden=\\"true\\"><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div></div></div><ul>\\n<li><a href=\\"https://github.com/THZoria/NX_Firmware\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">THZoria/NX_Firmware</a> —— 离线固件下载</li>\\n</ul>\\n</li>\\n<li>\\n<p>大佬</p>\\n<ul>\\n<li><a href=\\"https://www.youtube.com/@carcaschoi\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">carcaschoi</a></li>\\n<li><a href=\\"https://space.bilibili.com/36010\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">illyasever</a></li>\\n<li>参考：\\n<ul>\\n<li><a href=\\"https://www.marsshen.com/posts/20e16ead/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">https://www.marsshen.com/posts/20e16ead/</a></li>\\n<li>\\n</li>\\n</ul>\\n</li>\\n</ul>\\n</li>\\n</ul>","autoDesc":true}');export{R as comp,z as data};
