---
title: Java Web 功能
date: 2024-06-27
tag:
  - web
order: 33
---

## 坑：URL 中的空格转义问题

> 参考：
>
> - 如何正确地 urlEncode？空格被 urlEncode 成 `+`- <https://www.arloor.com/posts/how-to-urlencode/>

观察下面代码：

:::::: tabs

@tab 代码一: URLEncoder

URL（Uniform Resource Locator，统一资源定位器，被叫做 “网络地址” 或 “链接”） —— 在 Internet 上可以找到资源的位置的文本字符串。例如 `https://developer.mozilla.org`

遵循：

- W3C 标准 [HTML 4.01 规范](https://www.w3.org/TR/html4/interact/forms.html#h-17.13.4.1) 规定：
  当 `Content-Type` 为 `application/x-www-form-urlencoded` 时，URL 中查询参数名和参数值中空格要用加号 `+` 替代，所以几乎所有使用该规范的浏览器在表单提交后，URL 查询参数中空格都会被编成加号 `+`。

```java
String string = "+ +";
try {
    string = URLEncoder.encode(string, "UTF-8"); // 通过 URLEncode 处理，空格 " " 会被处理成加号 "+"。
    System.out.println(string); // %2B+%2B
    String res = URLDecoder.decode(string,"UTF-8"); // 通过 URLDecoder 处理，会把加号 "+" 和 "%20" 都解码为 " "。
    System.out.println(res); // + +
} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}
```

::: tip

Java 中的 URLEncoder 本意是用来把字符串编码成 `application/x-www-form-urlencoded` MIME 格式字符串，也就是说仅仅适用于 URL 中的查询字符串部分，但是 URLEncoder 经常被用来对 URL 的其他部分编码
（如：`https://www.example.org/你 好.jps?x=世 界` 中的 `你 好` 和 `世 界`）。

:::

@tab 代码二：URI

URI（Uniform Resource Identifier，统一资源标识符） —— 指向资源的字符串

遵循：

- [RFC2396](https://datatracker.ietf.org/doc/html/rfc2396)/[RFC1738](https://datatracker.ietf.org/doc/html/rfc1738) 规范规定：
  URI 里的保留字符都需转义成 `%HH` 格式(Section 3.4 Query Component)，因此空格会被编码成 `%20`，加号 `+`本身也作为保留字而被编成 `%2B`

```java
try {
    URI uri = new URI("http", "www.example.org", "/path/../to/你 好", "aa=b b&哦 哦=牛 逼", "fragment哈哈");
    System.out.println(uri);
    System.out.println(uri.toString()); // 空格 " " 转为 "%20"
    System.out.println(uri.normalize()); // ↑ + /path/../to 转为 /to
    System.out.println(uri.normalize().toASCIIString()); // ↑ + all to %HH
} catch (URISyntaxException e) {
    throw new RuntimeException(e);
}
```

::::::
