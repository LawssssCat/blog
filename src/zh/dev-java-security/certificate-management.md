---
title: 证书安全
date: 2024-11-16
order: 66
tag:
  - openssl
---

工具：

- 在线查看证书/证书转换工具
  - https://www.lddgo.net/encrypt/view-certificate （工具集，功能包括：查看/转换/...）
  - https://myssl.com/cert_convert.html （工具集）
  - https://www.qgj.cc/ （工具集）

## 术语/文件后缀

**术语**

机构名、格式/编码名、文件作用定义

**后缀**

搞清楚各种文件，如 `keystore`、`jks`、`p12`、`pfx`、`crt`、`csr`、`pem` 的格式、编码和用途。

- `.pem`：通用的 ASCII 编码的密钥和证书格式，可用于多种目的。
- `.key`：通常是以 PEM (Privacy Enhanced Mail) 格式存储的二进制数据。
- `.csr`：证书签名请求文件，用于向证书颁发机构申请证书。
- `.crt`： X.509 格式的公钥证书文件。
- `.p12`/`.pfx`： 跨平台的标准格式，包含私钥、证书和证书链。
- `.keystore`/`.jks`：Java 应用程序使用的密钥库格式，包含私钥和证书。

### 机构

- RSA —— todo
- IETF —— todo

### 规则

#### RFC（Request for Comments，请求意见稿）

规范版本

todo 本文基于的规范版本

### 格式/编码

#### `X.509`

> 参考：
>
> - X509 证书详解 （[link-en-OpenSSL Certificate Authority](https://jamielinux.com/docs/openssl-certificate-authority/index.html),[link-en-X509 证书详解](https://blog.csdn.net/blue0bird/article/details/78656536),[link-cn-X509 证书详解（中文翻译）](https://www.cnblogs.com/nirvanan/articles/13815185.html)）

`X.509` 标准是密码学里公钥**证书的格式标准**。
`X.509` 证书己应用在包括 TLS/SSL（WWW 万维网安全浏览的基石）在内的众多 Internet 协议里，同时它也有很多非在线的应用场景，比如电子签名服务。
`X.509` 证书含有公钥和标识（主机名、组织或个人），并由证书颁发机构（CA）签名（或自签名）。

`X.509` 其核心是根据 RFC5280 （通常称为 PKIX for Public Key Infrastructure（X.509））编码或数字签名的数字文档。
`X.509` 证书通常指的是 IETF 的 PKIX 证书和 X.509 v3 证书标准的 CRL 文件。

todo 看完参考文档

#### PEM（Privacy Enhanced Mail）

**格式**： PEM（Privacy Enhanced Mail） 是一种通用的 ASCII 编码的密钥和证书格式。
（以 `-----BEGIN...` 开头，以 `-----END...` 结尾）

**内容**： 更多表示一种格式，内容可以仅是公钥、私钥、证书，也可以包含完整的证书链（包括公玥，私钥，和根证书）。
（具体是什么由使用时候决定，因此不建议用 `.pem` 作为后缀，而建议直接按用法命名后缀，能直观了解文件作用）

**用途**：
这种格式可以保存证书和私钥，有时我们也把 PEM 格式的私钥的后缀改为 `.key` 以区别证书与私钥。
具体你可以看文件的内容。
这种文件被广泛用于各种安全协议和应用程序（openssl/keytool/...）中，包括 SSL/TLS。

**示例命令**：

```bash
# 【生成】
# -x509                 Output an X.509 certificate structure instead of a cert request
# -nodes                Don't encrypt private keys; deprecated
openssl req -new -x509 -days 365 -nodes -out cert.pem -keyout key.pem

# 【查看】
openssl x509 -in certificate.pem -text -noout
```

**内容示例**：

```txt
-----BEGIN CERTIFICATE-----
MIIDcjCCAlqgAwIBAgIEMAdjIzANBgkqhkiG9w0BAQsFADBWMQswCQYDVQQGEwJj
bjELMAkGA1UECBMCYmoxCzAJBgNVBAcTAmJqMQ4wDAYDVQQKEwViYWlkdTEOMAwG
A1UECxMFYmFpZHUxDTALBgNVBAMTBHRlc3QwHhcNMjQwODIyMTEyNzE3WhcNMjQx
MTIwMTEyNzE3WjBcMQswCQYDVQQGEwJjbjELMAkGA1UECBMCYmoxCzAJBgNVBAcT
AmJqMQ4wDAYDVQQKEwViYWlkdTEOMAwGA1UECxMFYmFpZHUxEzARBgNVBAMTCnd3
dy5iby5vcmcwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCL+E9DNtAt
WYHzQNhGrYQkqFzHDiyWka5szqMgFfSt9dB8U336YwZBD0DxWzrR0vDSUkFRquV5
qD1k1iwC/rxobhku0Rb1HqrsYXdsUHSQoD3ixCh9xH2LnQcsNfKeL+eNkK9ZjU1V
gsRbpwk4Q7g9j8PwswMNuELPuK96nE+w6JmYUrCR+VlZNAGpexzQME6xrWe0O9lf
VzwvovKFg4LCqKv1DoiZRcNaAtf4y9dYGyBhLkIvM+kdLeQFgKMogwsST9HhG148
i5EdYLA8KLTlWbDDqn3PG1id/UyA/Rh2kSHL2w4Er5cwyuh11Bz31fsfEU5GbR4D
QOw3lfjU4YEbAgMBAAGjQjBAMB8GA1UdIwQYMBaAFE2J8eJn74wQObiJv9iG6KpE
AOZVMB0GA1UdDgQWBBTA5yAHSEN4REiIA1ZeJ0PCesr4WjANBgkqhkiG9w0BAQsF
AAOCAQEACBa042+9RqXW4Mn5Vl2EWVCGkJl7lsRJc/LUQPBsjDlqEbCOA4Ilz5rN
HzO+idbKGBaSUpLG53OllXYODbWOVmKjPy+NrzyGbbEgU22WcdYq5lZgUHMMIvNI
kzm1KVC/BCNY8PsBOLk+pIzdnoQFlD9mYWt32LY6ao/Opuq/TYJ6FkMGrD9ECWau
i6NrgyQHEHIOQg4q189PTQaWbtz3+v3fKEyi6UsHEP7pOun2XSnFlYaa3l7oH4Md
lpi89ugQog2IpuY5TJ61ka+kAajBaVSIeE0/NOXGBlGCF3ptzNRq3vGPro86aPYP
Zefi3motl1RLsGg+CeWhlwWH0d2MwA==
-----END CERTIFICATE-----
```

#### DER（Distinguished Encoding Rules，可辨别编码规则）

**格式**： DER（Distinguished Encoding Rules，可辨别编码规则） 是一种二进制编码的 PEM 密钥和证书格式。
（相同的内容，一般 DER 格式文件比 PEM 格式的文件小）

::: tip
注意 der 不是一种文件格式，而是是 `ASN.1` 众多编码方案中的一个，使用 der 编码方案编码的 pem 文件。

因此，正确的说法是 “我有一个 DER 编码的证书” 而不是 “我有一个 DER 证书”。
:::

**内容**： 只保存证书，不保存私钥 （❗ 与 PEM 不同）

**用途**：
该格式是二进制文件内容，只保存证书，不保存私钥。
Java 和 Windows 服务器偏向于使用这种编码格式。

**示例命令**：

```bash
# 【生成】
openssl req -new -x509 -days 365 -nodes -outform der -out cert.der -keyout key.pem

# 【查看】
openssl x509 -in certificate.der -inform der -text -noout

# 【转换】
# ascii 编码的 pem 文件转成 der 编码
openssl x509 -in my_key_store.crt -out my_key_store.cer -outform der
# der 编码换成 ascii 编码的 pem 文件
openssl x509 -inform der -in to-convert.der -out converted.pem -outform pem
```

### 私钥文件

#### `.key`

**格式**： 通常是以 PEM (Privacy Enhanced Mail) 格式存储的二进制数据。

**内容**： 私钥， 通常用于 OpenSSL 中。
（`.key` 其实就是一个 pem 格式只包含私玥的文件，`.key` 作为文件名只是作为一个明显的别名。）

**用途**： 用于建立加密连接，如 HTTPS、SFTP 等。

**示例命令**：

```bash
openssl genpkey -algorithm RSA -out server.key
```

**示例内容**：

```txt
Bag Attributes
    friendlyName: www.bo.org
    localKeyID: 54 69 6D 65 20 31 37 32 34 38 34 31 35 37 38 35 39 31
Key Attributes: <No Attributes>
-----BEGIN PRIVATE KEY-----
MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCL+E9DNtAtWYHz
QNhGrYQkqFzHDiyWka5szqMgFfSt9dB8U336YwZBD0DxWzrR0vDSUkFRquV5qD1k
1iwC/rxobhku0Rb1HqrsYXdsUHSQoD3ixCh9xH2LnQcsNfKeL+eNkK9ZjU1VgsRb
pwk4Q7g9j8PwswMNuELPuK96nE+w6JmYUrCR+VlZNAGpexzQME6xrWe0O9lfVzwv
ovKFg4LCqKv1DoiZRcNaAtf4y9dYGyBhLkIvM+kdLeQFgKMogwsST9HhG148i5Ed
YLA8KLTlWbDDqn3PG1id/UyA/Rh2kSHL2w4Er5cwyuh11Bz31fsfEU5GbR4DQOw3
lfjU4YEbAgMBAAECggEAEemWIzDplVQmGD5NL0ZXaeWN8f9zX1WHb+9F2v5UUMrr
gm2g6qEvlLXiBsE5Fs8a+J+EMnJfaaFJMhVijoOREwA8AszJVlc1YojaoqbtM/D9
2n5l/CpgMrTGzTaeNPZGeMxyVFCL/Ax/GoeW23d3JZG3bp9KHcBd9H1bH6LPZAE+
YqPrBybLtUvGdmnmzE1FW1X6qcxI+chg6LQdYIad3Nvz7UPEFh6RTemEfyTNC+Gc
GBvusL0zQHq6GO8rdh8mAFqjqNc9qFlc8F+iuZVbquH6r2jPQrKhwXiwI+HFQXjG
Btf2MWF/UlrfBY6nda7xTLSrtDu26mQ+a700QRqYWQKBgQDzUNSdqGTY0ldHK2M5
jdCdL+EAw/gr/984lcAnN6g4thDMvlmqFq5x2tRAAVnXg6PCZihw8qLnydDNqfv+
M1Zoom4WkEQPn4TpT3a8zDYnomj2ADms/ydX7BHG3YpO5dnstkOWjzcom+iNC8pp
HwsJa2VJgu50G27GL93jDwAahwKBgQCTREc7wCpI6QqVAQcX5+5HW8lZi6kk3r2d
7NE9oBY/qB2h0w6RGzAygDmpQNCGS5wWaWUGqaFGQd/VBGVoJWnMuif/CFnXMA6F
kKqUztefWsJofa321DrNoEtgoG3mP/r0Q6bhPhxCoeGc71f2zhEWZthq7bIm9PDs
AXFU+kNlzQKBgHzRtoC6ZiGkpglohJp92csJSM/vuFw6AvUwPUbhEnclTzD5ZV4Q
DKVzsPa0urYIXfXYGl722gM2UNtwnEknZOiAiyEgGQo6tyBJF4x65j0m39ly/CN3
MWO1QtlVxSH8X/NC/SE7jvxSHtZcehW7Sxol8evoMxN1Dzq0S4uzokX5AoGAFWxz
aG/WNvEgBvk1TPcCpQLnUc2Nd72nnBfdgLePRQdx+B66GcX4xv/8Y3D4ZPEO7fu7
JLL8sTnYFCclkXcP4yaZtvJCD5oAItIe3rMQjzAm5AiUjDnnrA5LqOztz/cyzOIi
ntXoSpE+PxRamZ70wWuIxFV7+0ra2ZqEIDagI2kCgYArKiGNpEyqp3zZ68GAgTKJ
0oRjXvzZSsl5apSGkZy9oPjPEU7F0CS/YalXMmcvAfNUGQWhB1EfG1pgRvwObdBZ
SK8MDXAGvHWQGUlDtyHMyW6YQ15bRfw6xlPz9zepwapPWEca1mC9r6EOiX+aJEbD
ccrxFmPUfkDTjk8Pi9Bvcw==
-----END PRIVATE KEY-----
```

### 证书请求

#### CSR（Certificate Signing Request）

**格式**： CSR（Certificate Signing Request） 是一个由 RFC2986 定义的 PKCS10 格式的证书签名请求文件。

**内容**： 公钥和一些标识信息（比如：主题、 机构、国家等），用于向证书颁发机构（CA）申请证书。CA 确认申请的正确性后返回一张证书（PEM/..）。

**用途**： 用于向证书颁发机构提交证书申请。

**示例命令**：

```bash
# 生成
openssl req -new -key key.pem -out request.csr
keytool -certreq -alias www.bo.org -keystore d:\keystore\bo.keystore -file d:\keystore\cert.csr
```

**示例内容**：

```txt
-----BEGIN NEW CERTIFICATE REQUEST-----
MIIC0TCCAbkCAQAwXDELMAkGA1UEBhMCY24xCzAJBgNVBAgTAmJqMQswCQYDVQQHEwJiajEOMAwG
A1UEChMFYmFpZHUxDjAMBgNVBAsTBWJhaWR1MRMwEQYDVQQDEwp3d3cuYm8ub3JnMIIBIjANBgkq
hkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi/hPQzbQLVmB80DYRq2EJKhcxw4slpGubM6jIBX0rfXQ
fFN9+mMGQQ9A8Vs60dLw0lJBUarleag9ZNYsAv68aG4ZLtEW9R6q7GF3bFB0kKA94sQofcR9i50H
LDXyni/njZCvWY1NVYLEW6cJOEO4PY/D8LMDDbhCz7ivepxPsOiZmFKwkflZWTQBqXsc0DBOsa1n
tDvZX1c8L6LyhYOCwqir9Q6ImUXDWgLX+MvXWBsgYS5CLzPpHS3kBYCjKIMLEk/R4RtePIuRHWCw
PCi05Vmww6p9zxtYnf1MgP0YdpEhy9sOBK+XMMroddQc99X7HxFORm0eA0DsN5X41OGBGwIDAQAB
oDAwLgYJKoZIhvcNAQkOMSEwHzAdBgNVHQ4EFgQUwOcgB0hDeERIiANWXidDwnrK+FowDQYJKoZI
hvcNAQELBQADggEBAFk2EwCoHwk9QcYeOaEHGDfeRyC7M/0gMkc4EKzz7n2X6l8xOHcM8dMFLyh8
c4o/Lp3gS0lo+fbEFF8VXUmqY/S9lqrzja9w/PndiMcTqF2KdsfNFnU0L16e/E10zK2/2HmDwhPt
fAAvQ/8s8NYN5DG1gS+gvWBlaRY40iARiqaevJdgmD0c4Mvsxl9BphQR58qN13+eheuA9iLojrgR
KegIymB2wq1vBX9RnRFr6wDDuhZ7YEIBLnuHd1aCPtxuGuU9Pp2E0gWp5QjVmSGic/mrJ/bS6IfZ
5LNxW44TDlKmPOTnlPGIDEeJq9A7qsOECCHYMvibgrEbTSPDRMD8r7I=
-----END NEW CERTIFICATE REQUEST-----
```

### 证书文件

#### `.cert`/`.cer`/`.crt`

Certificate 的简称

**格式**： 有可能是 PEM 编码格式，也有可能是 DER 编码格式。

**内容**： 只包含公钥证书，不保存私钥

**用途**： 用于安装到 Web 服务器上，以便向客户端证明服务器的身份。

**示例命令**：

```bash
# 将.pem结尾的PEM格式证书转换成.crt结尾的PEM格式证书 （内容完全一样，就是改了个后缀）
openssl x509 -in cert.pem -outform PEM -out cert.crt
# 从密钥库导出证书
keytool -exportcert -keystore d:\keystore\bo.keysotre -alias www.bo.org -file d:\keystore\bo.crt
```

### 密钥库/证书库

私钥、证书和证书链

#### `.pkcs7`/`.p7b`/`.p7c` （过时，一般改用 p12）

`PKCS#7` 或 `P7B` 格式通常以 Base64 ASCII 格式存储，文件扩展名为 `.p7b` 或 `.p7c`。
P7B 证书包含“`----- BEGIN PKCS7 -----`”和“`----- END PKCS7 -----`”语句。
P7B 文件仅包含证书和链证书，而不包含私钥。多个平台支持 P7B 文件，包括 Microsoft Windows 和 Java Tomcat。

```bash
# 【转换】
# 将P7B转换为PEM
openssl pkcs7 -print_certs -in certificate.p7b -out certificate.cer
# 将P7B转换为PFX
openssl pkcs12 -export -in certificate.cer -inkey privateKey.key -out certificate.pfx -certfile CACert.cer
```

#### `.pkcs12`/`.pfx`/`.p12` - PKCS（Public-Key Cryptography Standards，公玥密码学）

**格式**： `PKCS#12` 是一种二进制的跨平台的标准格式。
（RSA 定义的 “描述个人信息交换语法标准” 有多个标准，pkcs（Public-Key Cryptography Standards，公玥密码学）标准是其中一个）

**内容**： 私钥、证书和证书链。
（一般同时包含证书和私钥，且有密码保护）

**是否加密**： 是，创建时需要输入仓库秘钥，读取时校验秘钥后才能读取出其中信息

**用途**：
用于多种环境，不仅限于 Java，还可以用于 .NET 应用、IOS 应用等。
在 Windows 计算机上通常用于导入和导出证书和私钥。

**示例命令**：

```bash
# 【生成】
openssl pkcs12 -in file-to-convert.p12 -out converted-file.pem -nodes

# 【查看】
openssl pkcs12 -in for-iis.pfx

# 【转换】
# 用 openssl 可以把 pkcs12 转换成包含公玥和私玥的 .pem 文件
# 💡OpenSSL会将所有证书和私钥放入一个文件中。您需要在文本编辑器中打开该文件，并将每个证书和私钥（包括BEGIN / END语句）复制到其各自的文本文件中，并将它们分别保存为certificate.cer，CACert.cer和privateKey.key
openssl pkcs12 -in for-iis.pfx -out for-iis.pem -nodes
```

#### `.jks`/`.keystore` - JKS（Java Key Storage）

**格式**： `.keystore` 或 `.jks`（Java Key Storage） 是 **Java 密钥库系统的默认格式**。

**内容**： 私钥、公钥证书，以及可能的其他证书链。
（一般同时包含证书和私钥，且有密码保护）

**是否加密**： 是，一般需要输入仓库秘钥才能读取其中信息

**用途**： 主要用于 Java 应用程序的安全配置，如 Web 服务器的身份验证。

::: tip
具体文件格式的选择取决于具体的需求和所使用的平台。
例如：
如果你是在 Java 环境中工作，可能会使用 `.keystore` 或 `.jks` 文件；
如果你需要跨平台兼容性，则可能更倾向于使用 `.p12` 或 `.pfx` 文件。
:::

**示例命令**：

```bash
# 生成
keytool -genkey -alias myalias -keyalg RSA -keysize 2048 -validity 365 -keystore mykeystore.keystore
keytool -genkey -alias myalias -keyalg RSA -keysize 2048 -validity 365 -keystore mykeystore.jks

# 【转换】
# jks 转成 p12
keytool -importkeystore -srckeystore bo.keystore -srcalias www.bo.org -destkeystore bo.p12 -deststoretype pkcs12
```

### 吊销列表

> 参考：
>
> - HTTPS 证书吊销机制 —— https://www.secpulse.com/archives/113075.html
> - OSCP 流程和实践 —— https://blog.csdn.net/anjiyufei/article/details/141951363 （⭐⭐⭐）

#### `.crl` - CRL（Certificate Revocation List，证书吊销列表）

背景：
当证书秘钥被泄漏，就有网站被中间人攻击的风险，这种情况需要向 CA 申请证书吊销。

**CRL**：
CRL（Certificate Revocation List，证书吊销列表）是 PKI 系统中的一个结构化数据文件，该文件包含了证书颁发机构 (CA) 已经吊销的证书的序列号、吊销日期。
（CRL 文件中还包含证书颁发机构信息、吊销列表失效时间和下一次更新时间，以及采用的签名算法等。）

todo CRL 运作流程

```bash
openssl ca -config intermediate/openssl.cnf -gencrl -out intermediate/crl/intermediate.crl.pem
```

#### OCSP（Online Certificate Status Protocol，证书状态在线查询协议）

**OCSP**：
一般 CA 都只是每隔一定时间 ( 几天或几个月 ) 才发布新的吊销列表，所以 CRL 是不能及时反映证书的实际状态的。
再加上 CRL 体积巨大，一般 1M 以上。
所以设计出 OCSP（Online Certificate Status Protocol，证书状态在线查询协议）以满足实时在线查询证书状态的要求。
OCSP 是 IETF 颁布的用于实时查询数字证书在某一时间是否有效的标准。

特点：

- 时效上 —— 实时查询
- 体积上 —— 小（请求者发送查询请求， OCSP 服务器会返回证书可能的三个状态：正常、吊销和未知。）

**OCSP 运作流程**：
浏览器在使用 `https://` 访问已经部署了 SSL 证书的网站时，一定会先检查此 SSL 证书是否已经被吊销，也就是说会查询吊销列表或 OCSP 服务，如果此证书已经被证书颁发机构吊销，则会显示警告信息： “此组织的证书已被吊销。安全证书问题可能显示试图欺骗您或截获您向服务器发送的数据。建议关闭此网页，并且不要继续浏览该网站。 ”

todo 细化流程 —— 梳理： 当要检查某个证书（下称待检查证书 A）是否被吊销时，客户端需要发送 OCSP 请求，OCSP 请求中附带“待检查证书 A”的序列号等信息，当 OCSP 服务器收到 OCSP 请求时，读取 OCSP 请求中“待检查证书 A”的序列号信息，并查询 CRL，得到检查结果，随后将检查结果封装到 OCSP 响应中，使用服务器私钥对 OCSP 响应进行签名并将签名私钥对应的证书（称为 OCSP 响应证书 B）和签名值附加在 OCSP 响应中。当客户端收到 OCSP 响应时，首先使用证书链对 OCSP 响应中的“OCSP 响应证书 B”进行验证，验证通过后，再使用“OCSP 响应证书 B”对 OCSP 响应中的响应值 tbsResponse 和签名值 signature 进行验签，验签通过，则表明 OCSP 响应合法，此时可读取 OCSP 响应中“待检查证书 A”的校验结果，以确认“待检查证书 A”是否被吊销。

```bash
# 获取 X509 证书
openssl s_client -connect cn.bing.com:443
# 查看 OCSP URL —— 证书/详细信息/授权信息访问/联机证书状态协议
openssl x509 -noout -ocsp_uri -in bing.crt
# OCSP验证 —— 在尾部显示Response Verify Ok，以及bing.crt检查结果是good
openssl ocsp -issuer "Microsoft Azure RSA TLS Issuing CA 04.crt" -CAfile chain.pem -cert bing.crt -text -url http://oneocsp.microsoft.com/ocsp
```

## openssl/keytool

::::::: tabs

@tab openssl 介绍

OpenSSL 是 SSL（Secure Sockets Layer） 的一个实现。

todo openssl 中文文档 —— https://www.openssl.net.cn/

todo openssl 英文文档 —— https://docs.openssl.org/master/

@tab keytool 介绍

keytool 是 Java 开发工具包 JDK1.4 之后引入的一个命令行工具，用于管理和生成 密钥对、数字证书 以及管理 密钥库。它主要用于安全通信和身份验证，通过使用公钥/私钥对和相关证书实现自我认证。

- keytool 官方文档： https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html
- keytool 参考手册： https://www.chinassl.net/ssltools/keytool-commands.html （⭐⭐⭐ 非常有用！）
- 证书学习（一）keytool 工具使用介绍： https://blog.csdn.net/qq_33204709/article/details/141436594

keytool 将密钥和证书存储在所谓的 “密钥库” 中，这是一种安全的存储设施，可以保护敏感信息免受未经授权的访问。

```bash
keytool -v
用法错误: 没有提供命令
密钥和证书管理工具

命令:

 -certreq            生成证书请求
 -changealias        更改条目的别名
 -delete             删除条目
 -exportcert         导出证书
 -genkeypair         生成密钥对
 -genseckey          生成密钥
 -gencert            根据证书请求生成证书
 -importcert         导入证书或证书链
 -importpass         导入口令
 -importkeystore     从其他密钥库导入一个或所有条目
 -keypasswd          更改条目的密钥口令
 -list               列出密钥库中的条目
 -printcert          打印证书内容
 -printcertreq       打印证书请求的内容
 -printcrl           打印 CRL 文件的内容
 -storepasswd        更改密钥库的存储口令

使用 "keytool -?, -h, or --help" 可输出此帮助消息
使用 "keytool -command_name --help" 可获取 command_name 的用法。
使用 -conf <url> 选项可指定预配置的选项文件。
```

主要功能：

1. **生成密钥对**： 可以创建新的公钥和私钥对，并将其存储在指定的密钥库。
1. **生成证书请求（CSR）**： 可以生成证书签名请求（Certificate Signing Request），并将其提交给 CA（证书办法机构）进行签发。
1. **导入和导出证书**： 可以从密钥库中导入或导出证书，支持多种格式。
1. **查看和操作密钥库**： 可以列出密钥库中的所有条目，包括公钥、私钥和证书链等信息。
1. **转换密钥库格式**： 可以将密钥库从一种格式转换为另一种格式，例如从 PKCS12 转换为 Jks。
1. **自签名证书**： 可以创建自签名的证书，用于测试和开发环境中的安全通信。
1. **证书吊销列表文件**： 可以生成证书吊销列表文件，用于跟踪已撤销的证书。

:::::::

### 查看

#### PEM/DER

```bash
# PEM
openssl x509 -in cert.pem -text -noout
openssl x509 -in cert.cer -text -noout
openssl x509 -in cert.crt -text -noout

# DER
openssl x509 -in certificate.der -inform der -text -noout
```

#### p12

```bash
# 显示与该证书相关的所有信息，包括证书的版本、序列号、持有人信息等
openssl pkcs12 -in example.p12 # 默认补全缺省 -info
openssl pkcs12 -info -in example.p12
# 查看p12证书中包含的证书链（通常包括公钥证书和中间证书，包括每个证书的版本、序列号、签发者信息等）
openssl pkcs12 -in example.p12 -nokeys -clcerts
# 显示私钥的详细信息，包括版本、算法、长度等
openssl pkcs12 -in example.p12 -nodes -nocerts
```

#### JKS

```bash
keytool -list -keystore server.jks
keytool -list -v -keystore yourjks.jks
```

### 生成

#### p12

```bash
# 【生成 p12 文件】
keytool -genkey -alias 别名 -keypass 密码 -keyalg RSA -keysize 2048 -validity 365 -keystore  文件路径/文件名.p12 -storepass 密码 -deststoretype pkcs12
```

#### jks

```bash
# 【生成 jks 文件】
# 解释：
# -genkey = -genkeypair
# keytool -genkey -alias test（别名）
# -keypass 123123（私钥密码）
#     "DSA" (when using -genkeypair)
#     "DES" (when using -genseckey)
# -keyalg RSA（算法）
# -sigalg sha256withrsa（算法小类）
# -keysize 1024（密钥长度）
#     2048 (when using -genkeypair and -keyalg is "RSA")
#     2048 (when using -genkeypair and -keyalg is "DSA")
#     256 (when using -genkeypair and -keyalg is "EC")
#     56 (when using -genseckey and -keyalg is "DES")
#     168 (when using -genseckey and -keyalg is "DESede")
# -validity 365（有效期）
# -keystore d:/keystore/test.jks（生成路径） 如果未指定，将使用 .keystore 文件。
# -storepass 123123（主密码）
keytool -genkey -alias test -keypass 123123 -keyalg RSA -sigalg sha256withrsa -keysize 1024 -validity 365 -keystore d:/keystore/test.jks -storepass 123123
```

### 编辑

#### `.jks` 修改别名及密码

```bash
keytool -changealias -keystore yourjks.jks -alias oldalias -destalias newalias
```

### 转换

#### 去除密码

去除 pem 格式的 key 的密码：
（输出的密码不输入即可）

```bash
openssl rsa -in test.key -out test1.key
```

#### `.der` 转 `.pem` （以及反向）

二进制转 base64

DER \-\-\> PEM

```bash
# 将der格式证书转pem格式
openssl x509 -in cert.crt -inform der -outform pem -out cert.pem
```

PEM \-\-\> DER

```bash
openssl x509 -in cert.crt -outform der -out cert.der
```

#### `.p12` 转 `.crt` + `.key`

```bash
openssl pkcs12 -in keystore.p12 -nokeys -out my_key_store.crt
openssl pkcs12 -in keystore.p12 -nocerts -nodes -out my_store.key
```

#### `.crt` + `.key` 转 `.p12`

合并 PEM 格式输出 PFX(p12)

```bash
openssl pkcs12 -export -in server.crt -inkey server.key -out mycert.p12
# 指定intermedian和CA
openssl pkcs12 -export -in server.crt -inkey server.key -out mycert.p12 -name alias_name -CAfile myCA.crt
openssl pkcs12 -export -in server.crt -inkey server.key -out mycert.p12 -name alias_name -CAfile myCA.crt -certfile server.crt
```

#### `.der`/`.pem` 转 `.p12` （以及反向）

```bash
openssl pkcs12 -in cert2.pfx -out cert22.pem -nodes
```

#### `.der`/`.pem` 转 `.jks` （以及反向）

CER \-\-\> JKS：
导入证书/秘钥

```bash
keytool -import -keystore cert.jks -file cert.der
# 别名
keytool -import -keystore cert.jks -file cert.der -alias youralias
keytool -import -keystore cert.jks -file cert.der -alias youralias -keypass youraliaspass
keytool -import -keystore cert.jks -file cert.der -alias youralias -keypass youraliaspass -v -noprompt
```

JKS \-\-\> CER：
导出证书/秘钥

```bash
keytool -export -alias test -keystore test.jks -storepass 123456 -file test.cer
```

#### `.p12` 转 `.jks` （以及反向）

P12 \-\-\> JKS

```bash
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 -deststoretype JKS -destkeystore keystore.jks
# 指定别名
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 -deststoretype JKS -destkeystore keystore.jks -srcalias alias_name -destalias alias_name
# 指定密码
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype pkcs12 -deststoretype jks -destkeystore keystore.jks -srcstorepass a123456 -deststorepass b123456 -v
```

JSK \-\-\> P12

```bash
keytool -importkeystore -srckeystore keystore.jks -srcstoretype JKS -deststoretype PKCS12 -destkeystore keystore.p12
```

#### `.jks` 转 `.crt` + `.key`

```bash
# 导出到server.cer文件中 server条目 server.jks密钥库 密钥库密码
keytool -exportcert -file server.cer -alias server -keystore server.jks -storepass 123456

# todo to .key
```

### 组合

在某些情况下，将多个 `X.509` 基础设施组合到单个文件中是有利的。一个常见的例子是将私钥和公钥两者结合到相同的证书中。

#### PEM 组合

组合密钥和链的最简单的方法是将每个文件转换为 PEM 编码的证书，然后将每个文件的内容简单地复制到一个新文件中。
这适用于组合文件以在 Apache 中使用的应用程序。

todo 例子

### 提取

一些证书将以组合形式出现。 一个文件可以包含以下任何一个：证书，私钥，公钥，签名证书，证书颁发机构（CA）和/或权限链。

todo 方式

### 导入

```bash
# 将自签名证书导入信任库
# 导入证书 server.cer server条目 导入client_trusk.jks信任库 密钥库密码 条目密码
keytool -importcert -file server.cer -alias server -keyalg client_trusk.jks -storepass 123456 -keypass 123456

# todo client-Trusk.jks 哪里来的？
```

## 案例：生成 CA + CSR + CRT + P12 + JSK （完整流程）

### 生成 CA 证书和私钥 （可选）

::: tip
如果你准备使用公共 CA 则不需要这一步，但是如果这个证书只是在我们自己的服务端和客户端之间使用则只需要使用自己的 CA
:::

::::: warning

使用 openssl 之前先要在当前目录下准备一个临时目录结构，如下结构：

```txt
 --demoCA/
 |-- index.txt      <-- 初始为空白内容
 |-- serial         <-- 初始内容可以设置为01
 |-- newcerts/      <-- 空文件夹
 |-- private/       <-- 貌似可以不存在
```

否则会后面使用自己的 ca 签署证书时会报错。

:::::

创建 CA 私钥

```bash
openssl genrsa -out ca.key 2048
```

创建 CA 证书

```bash
openssl req -new -x509 -days 3650 -key ca.key -out ca.crt -subj "/C=CN/ST=ZheJiang/L=HangZhou/O=NetEase/OU=CA Test"
```

### 生成服务器证书签署请求 CSR 和私钥

创建服务器私钥

```bash
openssl genrsa -out server.key 2048
```

创建服务器证书签署请求 CSR

```bash
openssl req -new -days 365 -key server.key -out server.csr -subj "/C=CN/ST=ZheJiang/L=HangZhou/O=NetEase/OU=XX Server/CN=xxx.yyy.com"
```

### 使用自己的 CA 进行签署生成证书 CRT

```bash
openssl ca -in server.csr -out server.crt -cert ca.crt -keyfile ca.key
```

需要输入 2 次确认的 y

### 合并证书

使用文本工具打开 `server.crt` 文件，可以看到内容有 2 部分组成：

- 第一部分是 Certificate 开头的描述内容
- 第二部分是 `-----BEGIN CERTIFICATE-----` 开始的签名内容

我们把 `ca.crt` 的内容覆盖掉描述内容，让文件成为 2 个签名块，就可以看到

```bash
-----BEGIN CERTIFICATE-----
...
-----END CERTIFICATE-----
-----BEGIN CERTIFICATE-----
...
-----END CERTIFICATE-----
```

转换为 pkcs12 格式

```bash
#  -clcerts               Only output client certificates
#  -cacerts               Only output CA certificates
openssl pkcs12 -export -clcerts -in server.crt -inkey server.key -out server.p12
```

需要输入一个密码 123321

### 转换为 jks 格式

```bash
keytool -importkeystore -srckeystore server.p12 -destkeystore server.jks -srcstoretype pkcs12 -deststoretype jks
```

::: tip
keytool 是 jdk 的工具，安装好 jdk 可以找到
:::

然后我们可以验证下 jks 是否包含了完整的证书链

```bash
keytool -list -v -keystore server.jks
```

我们可以看到如下信息

```txt
别名名称： 1
创建日期： 2015-5-28
项类型: PrivateKeyEntry
认证链长度： 2 # 💡这里若要显示2，就需要在生成p12前，将ca证书拷贝入crt证书中。否则这里会显示1，因为p12里只有crt一个证书
认证 [1]:
所有者:CN=xxx.yyy.com, OU=XX Server, O=NetEase, ST=ZheJiang, C=CN
签发人:OU=CA Test, O=NetEase, L=HangZhou, ST=ZheJiang, C=CN
序列号:1
有效期: Thu May 28 14:35:55 CST 2015 至Fri May 27 14:35:55 CST 2016
```

## 案例：Tomcat SSL 单向认证（JKS）

为了在 Java 应用中配置 SSL，通常需要将 CRT 和 KEY 证书文件转换为 Java Keystore（JKS）格式。
下面将介绍如何从 CRT 和 KEY 文件创建 JKS，并在 Tomcat 服务器中进行配置。

1. 从 CRT 和 KEY 文件创建 JKS

   ```bash
   # 【private.key + certificate.crt 转 keystore.p12】
   # certificate.crt = 你的证书文件
   # private.key = 你的私钥文件
   # cacert.crt = CA证书文件，即根证书文件
   # changeit = 默认密码，修改它！
   openssl pkcs12 -export -in certificate.crt -inkey private.key -out keystore.p12 -name tomcat -CAfile cacert.crt -passout pass:changeit
   # 【keystore.p12 转 keystore.jks】
   keytool -importkeystore -destkeystore keystore.jks -srckeystore keystore.p12 -srcstoretype PKCS12 -srcalias tomcat -destalias tomcat -deststorepass changeit -srcstorepass changeit
   ```

1. 在 Tomcat 服务器中进行配置

   将生成的 `keystore.jks` 文件放置到 Tomcat 的 conf 目录下。然后，编辑 `conf/server.xml` 文件，找到 `<Connector>` 标签，添加或更新以下配置：

   ```xml title="conf/server.xml"
   <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS"
           keystoreFile="conf/keystore.jks" keystorePass="changeit"
           keyAlias="tomcat"
   />
   ```

   在这个配置中，port 指定了 SSL 连接的端口号，keystoreFile 指定了 JKS 文件的路径，keystorePass 是 JKS 文件的密码，keyAlias 是证书的别名。

1. 重启 Tomcat

   配置完成后，重启 Tomcat 服务器以应用新的配置。
   如果一切正常，你现在可以通过配置的 SSL 端口（如 8443）访问你的应用，并享受加密的安全连接。

## 案例：Spring SSL 单向认证（JKS）

1. 准备 JKS 密钥库

   ```bash
   keytool -genkey -keyalg RSA -alias selfsigned -keystore xxx.jks -storepass jks密码 -validity 360 -keysize 2048
   ```

1. 配置 SSL

   ```yml
   #是否启用SSL证书
   server.ssl.enabled=true
   #配置jks存放位置
   server.ssl.key-store=classpath:xxx.jks
   #https端口号
   server.port=8090
   #密钥库密码
   server.ssl.key-store-password=jks密码
   #密钥库类型(JKS类型)
   server.ssl.key-store-type=JKS
   ```

1. 配置 http 允许连接 （否则，http 访问会提示 `Bad Request. This combination of host and port requires TLS.`）

## 案例：Nginx 单向认证（JKS）

场景： 前后端分离且前后端交互需要用 HTTPS 协议，则需要在前端服务（一般 nginx）上配置 SSL 证书。如果后端由 Java 开发，一般拿到的 SSL 证书是 Java 版的 jks 证书；且前端客户提供的配置好基本环境的 nginx，所需要的证书是 crt 和 key 组合形式，因此需要进行证书转换。

主要步骤：

1. 拿到 jks 证书，和证书密码（没有密码的，拿刀找运维或找供应方，要求重写生成个带密码的）
1. 先将 jks 转换成 p12 格式，具体命令如下:

   ```bash
   # 转换证书库格式
   keytool -importkeystore -srckeystore C:\cert\server.jks -destkeystore C:\cert\server.p12 -srcstoretype jks -deststoretype pkcs12
   # 查看证书库
   keytool -deststoretype PKCS12 -keystore C:\cert\server.p12 -list
   ```

   输入命令后，会提示你三步骤，需要输入口令（建议输入相同的口令，就是你的 jks 口令，防止后续证书转换忘记密码）

   1. 输入目标密钥库口令
   1. 再次输入新口令
   1. 输入源密钥库口令

1. 将 p12 转换成 crt 证书, 命令如下

   ```bash
   openssl pkcs12 -in C:\cert\server.p12 -nokeys -clcerts -out C:\cert\server.crt
   ```

   ::: info
   直接从 jks 导出证书

   ```bash
   keytool -alias jwt -exportcert -keystore jwt.jks -file jwt.cer
   ```

   :::

1. 将 p12 生成非加密的 key, 命令如下：

   ```bash
   openssl pkcs12 -in C:\cert\server.p12 -nocerts -nodes -out C:\cert\server.key
   ```

1. 将证书配置到 nginx 以后，重启 nginx 服务器

   ```bash
   server {
       #监听端口和域名
       listen      443 ssl;
       server_name  localhost;
       #以下两个为证书文件
       ssl_certificate C:/cert/server.crt;
       ssl_certificate_key C:/cert/server.key;
       ssl_session_timeout 1m;
       ssl_protocols SSLv2 SSLv3 TLSv1.2;
       ssl_ciphers ECDHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES128-GCM-SHA256:ECDHE-RSA-AES256:AES128-SHA:AES256-SHA:RC4-SHA:DES-CBC3-SHA:RC4-MD5;
       ssl_prefer_server_ciphers   on;
       location / {
           root D:/nginx/portal;
           index index.html;
       }
   }
   # 访问80端口时转发到443端口，转为https访问
   server {
       listen       80;
       server_name  localhost;
       return 301 https://$host$request_uri;
   }
   ```

1. 使用 ssl 漏洞扫描工具，检验证书链的完整性，并获取证书链（防止小程序报: fail ssl hand shake error）。
   推荐地址 https://myssl.com/chain_download.html

   1. 如果证书有漏洞，或不完整，拷贝证书链以后，将服务器上 `server.crt` 内容替换，重启 nginx （或 `nginx -s reload`）
   1. 如果还不行（比如：网页访问，依然不安全），请清空缓存，刷新

## 附录

> 参考：
>
> - SSL 证书格式普及，PEM、CER、JKS、PKCS12 —— https://blog.freessl.cn/ssl-cert-format-introduce/
> - https://blog.csdn.net/qq_33204709/category_12778972.html
>   - todo 证书学习（一）keytool 工具使用介绍
>   - 证书学习（二）搞懂 keystore、jks、p12、pfx、cer、crt、csr、pem、key 文件的区别
>   - todo 证书学习（三）.p12 证书颁发的 5 个步骤、如何在线生成证书、证书工具网站推荐
>   - todo 证书学习（四）X.509 数字证书整理
>   - todo 证书学习（五）Java 实现 RSA、SM2 证书颁发
>   - todo 证书学习（六）TSA 时间戳服务器原理 + 7 个免费时间戳服务器地址
> - todo HTTPS 证书吊销机制 —— https://www.secpulse.com/archives/113075.html
> - todo OSCP 流程和实践 —— https://blog.csdn.net/anjiyufei/article/details/141951363
