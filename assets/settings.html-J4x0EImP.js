import{_ as e,o as t,c as s,a as n,d as a,f as i}from"./app-Ck_FxMtA.js";const l={},o=a("p",null,"介绍 maven settings.xml 和 pom.xml 配置文件的使用。",-1),r=i(`<p><strong>settings.xml</strong></p><p>settings.xml 是用来设置 maven 参数的配置文件。 settings.xml 中包含类似本地仓储位置、修改远程仓储服务器、认证信息等配置。</p><p><strong>pom.xml</strong></p><p>settings.xml 是 maven 的全局配置文件，而 pom.xml 文件是所在项目的局部配置。</p><h2 id="配置文件位置" tabindex="-1"><a class="header-anchor" href="#配置文件位置"><span>配置文件位置</span></a></h2><p>pom.xml 文件一般位于项目根目录。</p><p>settings.xml 文件一般存在于两个位置：</p><ul><li>全局配置: <code>\${M2_HOME}/conf/settings.xml</code></li><li>用户配置： <code>~/.m2/settings.xml</code></li></ul><h2 id="配置优先级" tabindex="-1"><a class="header-anchor" href="#配置优先级"><span>配置优先级</span></a></h2><p>核心： <strong>局部配置优先于全局配置</strong>。</p><p>配置优先级从高到低： pom.xml &gt; user settings &gt; global settings</p><div class="hint-container info"><p class="hint-container-title">相关信息</p><p>如果这些文件同时存在，在应用配置时，会合并它们的内容，如果有重复的配置，优先级高的配置会覆盖优先级低的。</p></div><div class="language-bash line-numbers-mode" data-ext="sh" data-title="sh"><pre class="shiki shiki-themes github-light one-dark-pro" style="background-color:#fff;--shiki-dark-bg:#282c34;color:#24292e;--shiki-dark:#abb2bf;" tabindex="0"><code><span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">mvn</span><span style="color:#005CC5;--shiki-dark:#D19A66;"> -X</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 查看 settings.xml 文件的读取顺序</span></span>
<span class="line"><span style="color:#6F42C1;--shiki-dark:#61AFEF;">mvn</span><span style="color:#032F62;--shiki-dark:#98C379;"> help:effective-settings</span><span style="color:#6A737D;--shiki-dark:#7F848E;font-style:inherit;--shiki-dark-font-style:italic;"> # 查看当前生效的 settings.xml</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="settings-xml-元素详解" tabindex="-1"><a class="header-anchor" href="#settings-xml-元素详解"><span>settings.xml 元素详解</span></a></h2><p>todo https://www.cnblogs.com/jingmoxukong/p/6050172.html?utm_source=gold_browser_extension</p>`,15);function p(m,c){return t(),s("div",null,[o,n(" more "),r])}const h=e(l,[["render",p],["__file","settings.html.vue"]]),g=JSON.parse('{"path":"/zh/dev-java-maven/settings.html","title":"Maven 配置","lang":"zh-CN","frontmatter":{"title":"Maven 配置","date":"2024-04-13T00:00:00.000Z","tag":["maven"],"order":10,"description":"介绍 maven settings.xml 和 pom.xml 配置文件的使用。","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/dev-java-maven/settings.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"Maven 配置"}],["meta",{"property":"og:description","content":"介绍 maven settings.xml 和 pom.xml 配置文件的使用。"}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-04-14T05:17:52.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"maven"}],["meta",{"property":"article:published_time","content":"2024-04-13T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-04-14T05:17:52.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"Maven 配置\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-04-13T00:00:00.000Z\\",\\"dateModified\\":\\"2024-04-14T05:17:52.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"配置文件位置","slug":"配置文件位置","link":"#配置文件位置","children":[]},{"level":2,"title":"配置优先级","slug":"配置优先级","link":"#配置优先级","children":[]},{"level":2,"title":"settings.xml 元素详解","slug":"settings-xml-元素详解","link":"#settings-xml-元素详解","children":[]}],"git":{"createdTime":1713013694000,"updatedTime":1713071872000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":2}]},"readingTime":{"minutes":0.83,"words":250},"filePathRelative":"zh/dev-java-maven/settings.md","localizedDate":"2024年4月13日","excerpt":"<p>介绍 maven settings.xml 和 pom.xml 配置文件的使用。</p>\\n","autoDesc":true}');export{h as comp,g as data};
