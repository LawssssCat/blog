---
title: Perl 使用笔记
---

[Perl（Practical Extraction and Report Language，实用报表提取语言）](https://zh.wikipedia.org/zh-cn/Perl)是高级、通用、直译式、动态的程序语言,其重要的特性是Perl内部集成了正则表达式的功能，以及巨大的第三方代码库[CPAN（the Comprehensive Perl Archive Network，全面的 Perl 存档网络 —— 提供扩展模块）](https://www.cpan.org/)。
由于其灵活性，Perl被称为脚本语言中的瑞士军刀。
愿景：Perl像C一样强大，像awk、sed等脚本描述语言一样方便。

```bash
#!/usr/bin/perl

print "Hello, World!\n";
```

<!-- more -->

## 编译

```bash
wget https://www.cpan.org/src/5.0/perl-5.28.0.tar.gz
tar -zxvf perl-5.28.0.tar.gz      
cd perl-5.28.0

PERL_HOME=`mktemp -d`
./Configure -des -Dprefix=$PERL_HOME
make
# make test
make install
```
