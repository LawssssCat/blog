import{_ as n,r as e,o as l,c as t,d as s,e as a,b as o,f as r}from"./app-BcXDjejK.js";const p={},h={href:"https://www.haoyep.com/posts/zsh-config-oh-my-zsh/",target:"_blank",rel:"noopener noreferrer"},c=r(`<h2 id="zsh" tabindex="-1"><a class="header-anchor" href="#zsh"><span>zsh</span></a></h2><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">sudo</span><span style="color:#032F62;--shiki-dark:#98C379;"> apt</span><span style="color:#032F62;--shiki-dark:#98C379;"> install</span><span style="color:#032F62;--shiki-dark:#98C379;"> zsh</span></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 将 zsh 设置为默认 shell</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">chsh</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -s</span><span style="color:#032F62;--shiki-dark:#98C379;"> /bin/zsh</span></span>
<span class="line"><span style="color:#005CC5;--shiki-dark:#56B6C2;">echo</span><span style="color:#24292E;--shiki-dark:#E06C75;"> $SHELL</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="oh-my-zsh" tabindex="-1"><a class="header-anchor" href="#oh-my-zsh"><span>oh-my-zsh</span></a></h2><p>官网： http://ohmyz.sh/</p><p>主题</p><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">powerlevel10k</span><span style="color:#032F62;--shiki-dark:#98C379;"> ——</span><span style="color:#032F62;--shiki-dark:#98C379;"> 这个主题对字体有要求，需要看</span><span style="color:#032F62;--shiki-dark:#98C379;"> Readme.md</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">vim</span><span style="color:#032F62;--shiki-dark:#98C379;"> ~/.zshrc</span></span>
<span class="line"><span style="color:#24292E;--shiki-dark:#E06C75;">ZSH_THEME</span><span style="color:#D73A49;--shiki-dark:#56B6C2;">=</span><span style="color:#032F62;--shiki-dark:#98C379;">&quot;powerlevel10k&quot;</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>插件</p><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 插件：提示 —— zsh-autosuggestions</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">git</span><span style="color:#032F62;--shiki-dark:#98C379;"> clone</span><span style="color:#032F62;--shiki-dark:#98C379;"> https://github.com/zsh-users/zsh-autosuggestions</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> \${</span><span style="color:#24292E;--shiki-dark:#E06C75;">ZSH_CUSTOM</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">:-</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">~</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">/</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">.</span><span style="color:#24292E;--shiki-dark:#E06C75;">oh-my-zsh</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">/</span><span style="color:#24292E;--shiki-dark:#E06C75;">custom</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">}</span><span style="color:#032F62;--shiki-dark:#98C379;">/plugins/zsh-autosuggestions</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 插件：高亮 —— zsh-syntax-highlighting</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">git</span><span style="color:#032F62;--shiki-dark:#98C379;"> clone</span><span style="color:#032F62;--shiki-dark:#98C379;"> https://github.com/zsh-users/zsh-syntax-highlighting.git</span><span style="color:#24292E;--shiki-dark:#ABB2BF;"> \${</span><span style="color:#24292E;--shiki-dark:#E06C75;">ZSH_CUSTOM</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">:-</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">~</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">/</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">.</span><span style="color:#24292E;--shiki-dark:#E06C75;">oh-my-zsh</span><span style="color:#D73A49;--shiki-dark:#ABB2BF;">/</span><span style="color:#24292E;--shiki-dark:#E06C75;">custom</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">}</span><span style="color:#032F62;--shiki-dark:#98C379;">/plugins/zsh-syntax-highlighting</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 插件：记录转跳 —— z （内置）</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 插件：解压 —— extract （内置）</span></span>
<span class="line"></span>
<span class="line"><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"># 插件：搜索 —— web-search （内置）</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>插件使用 <code>~/.zshrc</code></p><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#24292E;--shiki-dark:#E06C75;">plugins</span><span style="color:#D73A49;--shiki-dark:#56B6C2;">=</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">(</span><span style="color:#032F62;--shiki-dark:#98C379;">git</span><span style="color:#032F62;--shiki-dark:#98C379;"> zsh-autosuggestions</span><span style="color:#032F62;--shiki-dark:#98C379;"> zsh-syntax-highlighting</span><span style="color:#032F62;--shiki-dark:#98C379;"> z</span><span style="color:#032F62;--shiki-dark:#98C379;"> extract</span><span style="color:#032F62;--shiki-dark:#98C379;"> web-search</span><span style="color:#24292E;--shiki-dark:#ABB2BF;">)</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="neovim" tabindex="-1"><a class="header-anchor" href="#neovim"><span>neovim</span></a></h2><p>todo nvim 终端 ide</p>`,12);function d(k,y){const i=e("ExternalLinkIcon");return l(),t("div",null,[s("p",null,[a("参考： "),s("a",h,[a("https://www.haoyep.com/posts/zsh-config-oh-my-zsh/"),o(i)])]),c])}const u=n(p,[["render",d],["__file","zsh.html.vue"]]),b=JSON.parse('{"path":"/zh/ops-terminal-shell/zsh.html","title":"ZSH 介绍","lang":"zh-CN","frontmatter":{"title":"ZSH 介绍","date":"2024-06-09T00:00:00.000Z","tag":["linux"],"order":1,"description":"参考： https://www.haoyep.com/posts/zsh-config-oh-my-zsh/ zsh oh-my-zsh 官网： http://ohmyz.sh/ 主题 插件 插件使用 ~/.zshrc neovim todo nvim 终端 ide","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/ops-terminal-shell/zsh.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"ZSH 介绍"}],["meta",{"property":"og:description","content":"参考： https://www.haoyep.com/posts/zsh-config-oh-my-zsh/ zsh oh-my-zsh 官网： http://ohmyz.sh/ 主题 插件 插件使用 ~/.zshrc neovim todo nvim 终端 ide"}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-06-15T17:41:01.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"linux"}],["meta",{"property":"article:published_time","content":"2024-06-09T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-06-15T17:41:01.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"ZSH 介绍\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-06-09T00:00:00.000Z\\",\\"dateModified\\":\\"2024-06-15T17:41:01.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"zsh","slug":"zsh","link":"#zsh","children":[]},{"level":2,"title":"oh-my-zsh","slug":"oh-my-zsh","link":"#oh-my-zsh","children":[]},{"level":2,"title":"neovim","slug":"neovim","link":"#neovim","children":[]}],"git":{"createdTime":1717930991000,"updatedTime":1718473261000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":2}]},"readingTime":{"minutes":0.52,"words":157},"filePathRelative":"zh/ops-terminal-shell/zsh.md","localizedDate":"2024年6月9日","excerpt":"<p>参考： <a href=\\"https://www.haoyep.com/posts/zsh-config-oh-my-zsh/\\" target=\\"_blank\\" rel=\\"noopener noreferrer\\">https://www.haoyep.com/posts/zsh-config-oh-my-zsh/</a></p>\\n<h2>zsh</h2>\\n<div class=\\"language-bash line-numbers-mode\\" data-ext=\\"sh\\" data-title=\\"sh\\"><pre class=\\"shiki shiki-themes github-light one-dark-pro\\" style=\\"background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf\\" tabindex=\\"0\\"><code><span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">sudo</span><span style=\\"color:#032F62;--shiki-dark:#98C379\\"> apt</span><span style=\\"color:#032F62;--shiki-dark:#98C379\\"> install</span><span style=\\"color:#032F62;--shiki-dark:#98C379\\"> zsh</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic\\"># 将 zsh 设置为默认 shell</span></span>\\n<span class=\\"line\\"><span style=\\"color:#6F42C1;--shiki-dark:#61AFEF\\">chsh</span><span style=\\"color:#005CC5;--shiki-dark:#D19A66\\"> -s</span><span style=\\"color:#032F62;--shiki-dark:#98C379\\"> /bin/zsh</span></span>\\n<span class=\\"line\\"><span style=\\"color:#005CC5;--shiki-dark:#56B6C2\\">echo</span><span style=\\"color:#24292E;--shiki-dark:#E06C75\\"> $SHELL</span></span>\\n<span class=\\"line\\"></span></code></pre><div class=\\"line-numbers\\" aria-hidden=\\"true\\"><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div><div class=\\"line-number\\"></div></div></div>","autoDesc":true}');export{u as comp,b as data};
