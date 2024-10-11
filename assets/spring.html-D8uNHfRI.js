import{_ as t,o as i,c as e,f as n}from"./app-C2g-dteS.js";const o={},a=n('<h2 id="spring-扩展点" tabindex="-1"><a class="header-anchor" href="#spring-扩展点"><span>spring 扩展点</span></a></h2><ul><li>https://www.bilibili.com/video/BV1Mt42177MK/</li></ul><div class="hint-container tip"><p class="hint-container-title">提示</p><p>区别：</p><ul><li>Instantiation 实例化，对应 bean 的 new 过程</li><li>Initialization 初始化，对应 bean 初始化和销毁的 3 对方法</li></ul></div><p>Spring 提供多种 spring bean 类初始化/销毁时的扩展点：</p><ul><li>configuration 类实现 BeanPostProcessor 接口 —— 在每个 spring bean 初始化前/后调用接口方法 <ul><li>应用 <ul><li>ApplicationContextAwareProcessor（ApplicationContextAware 的原理）</li><li>AutowiredAnnotationBeanProcessor（使用 <code>@Autowire</code> 注入 spring bean 的原理）</li><li>CommonAnnotationBeanPostProcessor（使用 jsr250 的原理，如 <code>@PostConstruct</code>/<code>@PreDestroy</code>/... 等注解的原理）</li><li>aop 原理（<code>@EnableAspectJAutoProxy</code>）</li></ul></li></ul></li><li>configuration 类方法上添加 <code>@Bean</code> 注解，返回值就是创建的 spring bean 对象 <ul><li>initMethod —— 初始化方法</li><li>destroyMethod —— 销毁方法</li></ul></li><li>spring bean 类方法上添加 <code>@PostConstruct</code> 注解</li><li>spring bean 类实现 InitializingBean 接口（创建后）、实现 DisposableBean 接口（销毁前）</li></ul>',5),r=[a];function s(l,p){return i(),e("div",null,r)}const d=t(o,[["render",s],["__file","spring.html.vue"]]),g=JSON.parse('{"path":"/zh/dev-java-spring/spring.html","title":"Spring 介绍","lang":"zh-CN","frontmatter":{"title":"Spring 介绍","date":"2024-06-24T00:00:00.000Z","tag":["spring"],"order":1,"description":"spring 扩展点 https://www.bilibili.com/video/BV1Mt42177MK/ 提示 区别： Instantiation 实例化，对应 bean 的 new 过程 Initialization 初始化，对应 bean 初始化和销毁的 3 对方法 Spring 提供多种 spring bean 类初始化/销毁时的扩展点： ...","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/dev-java-spring/spring.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"Spring 介绍"}],["meta",{"property":"og:description","content":"spring 扩展点 https://www.bilibili.com/video/BV1Mt42177MK/ 提示 区别： Instantiation 实例化，对应 bean 的 new 过程 Initialization 初始化，对应 bean 初始化和销毁的 3 对方法 Spring 提供多种 spring bean 类初始化/销毁时的扩展点： ..."}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-06-24T00:01:47.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"spring"}],["meta",{"property":"article:published_time","content":"2024-06-24T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-06-24T00:01:47.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"Spring 介绍\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-06-24T00:00:00.000Z\\",\\"dateModified\\":\\"2024-06-24T00:01:47.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[{"level":2,"title":"spring 扩展点","slug":"spring-扩展点","link":"#spring-扩展点","children":[]}],"git":{"createdTime":1719187307000,"updatedTime":1719187307000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":1}]},"readingTime":{"minutes":0.65,"words":196},"filePathRelative":"zh/dev-java-spring/spring.md","localizedDate":"2024年6月24日","excerpt":"<h2>spring 扩展点</h2>\\n<ul>\\n<li>https://www.bilibili.com/video/BV1Mt42177MK/</li>\\n</ul>\\n<div class=\\"hint-container tip\\">\\n<p class=\\"hint-container-title\\">提示</p>\\n<p>区别：</p>\\n<ul>\\n<li>Instantiation 实例化，对应 bean 的 new 过程</li>\\n<li>Initialization 初始化，对应 bean 初始化和销毁的 3 对方法</li>\\n</ul>\\n</div>\\n<p>Spring 提供多种 spring bean 类初始化/销毁时的扩展点：</p>","autoDesc":true}');export{d as comp,g as data};