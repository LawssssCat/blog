import{_ as n,r as i,o as l,c as r,d as s,e,b as a,f as o}from"./app-v6wXHRU9.js";const p={},c=s("p",null,"todo",-1),h=s("p",null,"https://rustwiki.org/zh-CN/book/ch02-00-guessing-game-tutorial.html",-1),d=s("h2",{id:"文档",tabindex:"-1"},[s("a",{class:"header-anchor",href:"#文档"},[s("span",null,"文档")])],-1),u=s("p",null,"记录 Rust 相关的有用链接：",-1),k={href:"https://rustwiki.org/zh-CN/",target:"_blank",rel:"noopener noreferrer"},g={href:"https://rustwiki.org/zh-CN/book/ch01-03-hello-cargo.html",target:"_blank",rel:"noopener noreferrer"},m={href:"https://rustwiki.org/zh-CN/cargo/",target:"_blank",rel:"noopener noreferrer"},y={href:"https://letsgetrusty.com/bootcamp/",target:"_blank",rel:"noopener noreferrer"},b=s("p",null,"有用的视频：",-1),v={href:"https://www.youtube.com/watch?v=ZhedgZtd8gw",target:"_blank",rel:"noopener noreferrer"},f=o(`<h2 id="环境配置" tabindex="-1"><a class="header-anchor" href="#环境配置"><span>环境配置</span></a></h2><h3 id="一键配置-rustup" tabindex="-1"><a class="header-anchor" href="#一键配置-rustup"><span>一键配置：rustup</span></a></h3><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">curl</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> --proto</span><span style="color:#032F62;--shiki-dark:#98C379;"> &#39;=https&#39;</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> --tlsv1.2</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -sSf</span><span style="color:#032F62;--shiki-dark:#98C379;"> https://sh.rustup.rs</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;"> |</span><span style="color:#6F42C1;--shiki-dark:#61AFEF;"> sh</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h3 id="手动配置" tabindex="-1"><a class="header-anchor" href="#手动配置"><span>手动配置</span></a></h3><ul><li>rustc —— 编译器 (类 gcc)</li><li>cargo —— 构建器、包管理器 (类 makefile)</li></ul><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> new</span><span style="color:#032F62;--shiki-dark:#98C379;"> hello_cargo</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 创建项目</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> new</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> --vcs=git</span><span style="color:#032F62;--shiki-dark:#98C379;"> hello_cargo</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 创建项目，创建版本管理器</span></span>
<span class="line"><span style="color:#005CC5;--shiki-dark:#56B6C2;">cd</span><span style="color:#032F62;--shiki-dark:#98C379;"> hello_cargo</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 开发构建 + 运行</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> build</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 构建</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">./target/debug/hello_cargo</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # .\\target\\debug\\hello_cargo</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 发布构建 （构建更慢、运行更快）</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> build</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> --release</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">./target/release/hello_cargo</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # .\\target\\release\\hello_cargo</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> run</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 构建并运行</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">cargo</span><span style="color:#032F62;--shiki-dark:#98C379;"> check</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 检查语法</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="vscode" tabindex="-1"><a class="header-anchor" href="#vscode"><span>vscode</span></a></h3><p>参考：</p><ul><li>https://www.bilibili.com/video/BV1A4mjYjE6C/</li></ul><p>插件</p><ul><li>rust-analyzer —— 语法提示</li><li>CodeLLDB —— 调试工具</li><li>Even Better TOML —— 配置高亮</li><li>Dependi —— 依赖版本自动检索</li><li>error lens —— 提示高亮</li><li>todo tree —— 高亮并收集 <code>TODO/FIXME/...</code></li><li><s>GitHub Copilot</s> —— 代码 AI 生成</li></ul>`,11);function C(_,F){const t=i("ExternalLinkIcon");return l(),r("div",null,[c,h,d,u,s("ul",null,[s("li",null,[s("p",null,[e("Rust 官方文档中文翻译 —— "),s("a",k,[e("https://rustwiki.org/zh-CN/"),a(t)])]),s("ul",null,[s("li",null,[s("a",g,[e("Rust 程序设计语言（中文）"),a(t)])]),s("li",null,[s("a",m,[e("Cargo 手册（中文）"),a(t)])])])]),s("li",null,[s("p",null,[e("? Rust 培训 —— "),s("a",y,[e("https://letsgetrusty.com/bootcamp/"),a(t)])])])]),b,s("ul",null,[s("li",null,[e("配置 VSCode 的 rust 开发环境 —— "),s("a",v,[e("https://www.youtube.com/watch?v=ZhedgZtd8gw"),a(t)])])]),f])}const A=n(p,[["render",C],["__file","index.html.vue"]]),E=JSON.parse('{"path":"/zh/dev-rust-commonsense/","title":"Rust 语言通用概念","lang":"zh-CN","frontmatter":{"title":"Rust 语言通用概念","date":"2024-10-31T00:00:00.000Z","order":1,"description":"todo https://rustwiki.org/zh-CN/book/ch02-00-guessing-game-tutorial.html 文档 记录 Rust 相关的有用链接： Rust 官方文档中文翻译 —— https://rustwiki.org/zh-CN/ Rust 程序设计语言（中文） Cargo 手册（中文） ? Rust 培训 ...","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/dev-rust-commonsense/"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"Rust 语言通用概念"}],["meta",{"property":"og:description","content":"todo https://rustwiki.org/zh-CN/book/ch02-00-guessing-game-tutorial.html 文档 记录 Rust 相关的有用链接： Rust 官方文档中文翻译 —— https://rustwiki.org/zh-CN/ Rust 程序设计语言（中文） Cargo 手册（中文） ? Rust 培训 ..."}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-11-01T22:38:06.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:published_time","content":"2024-10-31T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-11-01T22:38:06.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"Rust 语言通用概念\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-10-31T00:00:00.000Z\\",\\"dateModified\\":\\"2024-11-01T22:38:06.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"文档","slug":"文档","link":"#文档","children":[]},{"level":2,"title":"环境配置","slug":"环境配置","link":"#环境配置","children":[{"level":3,"title":"一键配置：rustup","slug":"一键配置-rustup","link":"#一键配置-rustup","children":[]},{"level":3,"title":"手动配置","slug":"手动配置","link":"#手动配置","children":[]},{"level":3,"title":"vscode","slug":"vscode","link":"#vscode","children":[]}]}],"git":{"createdTime":1730500686000,"updatedTime":1730500686000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":1}]},"readingTime":{"minutes":0.86,"words":259},"filePathRelative":"zh/dev-rust-commonsense/README.md","localizedDate":"2024年10月31日","excerpt":"<p>todo</p>\\n<p>https://rustwiki.org/zh-CN/book/ch02-00-guessing-game-tutorial.html</p>\\n<h2>文档</h2>\\n<p>记录 Rust 相关的有用链接：</p>\\n<ul>\\n<li>\\n<p>Rust 官方文档中文翻译 —— <a href=\\"https://rustwiki.org/zh-CN/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">https://rustwiki.org/zh-CN/</a></p>\\n<ul>\\n<li><a href=\\"https://rustwiki.org/zh-CN/book/ch01-03-hello-cargo.html\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">Rust 程序设计语言（中文）</a></li>\\n<li><a href=\\"https://rustwiki.org/zh-CN/cargo/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">Cargo 手册（中文）</a></li>\\n</ul>\\n</li>\\n<li>\\n<p>? Rust 培训 —— <a href=\\"https://letsgetrusty.com/bootcamp/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">https://letsgetrusty.com/bootcamp/</a></p>\\n</li>\\n</ul>","autoDesc":true}');export{A as comp,E as data};