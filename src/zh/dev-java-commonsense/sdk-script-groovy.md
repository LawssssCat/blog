---
title: Groovy 使用笔记
date: 2024-07-18
tag:
  - java
  - gradle
order: 10
---

todo 项目 https://github.com/nextflow-io/nextflow
todo 项目 https://github.com/jenkinsci/job-dsl-plugin

Groovy 是可在 JVM 中运行的脚本语言。
同时， Groovy 有面向对象的特性。

::: info
其他可在 JVM 中运行的脚本语言有： JRuby, JPython, BeanShell, JavaScript, ...

相比起其他脚本语言，Groovy 的优势是能使用 Java 的库。
:::

Groovy 在 JSR241 中被 Java 引入，在 2007 年 1 月发布第一个版本。

<!-- more -->

官网: <https://www.groovy-lang.org/documentation.html> \
Github: https://github.com/apache/groovy \
特性： https://www.youtube.com/watch?v=BXRDTiJfrSE

## 运行

### console (web)

运行在网页控制台

场景：

- demo

<>

### console (local)

运行在本地 GDK 环境

场景：

- 开发/测试

#### 安装：方式一、gdk with manual

<https://groovy.codehaus.org/Download>

- Download Groovy development kit (GDK)
- install
- groovy/groovyConsole/groovesh
- 配置环境变量

:::::: info

Groovy 自带命令行编辑器，在 `${GROOVY_HOME}/bin` 下。

::: tabs

@tab linux

```bash
groovyConsole
```

@tab windows

```ps1
groovyConsole.bat
```

:::

::::::

#### 安装：方式一、gvm

<https://gvmtool.net/>

```bash
install msysgit
install GVM
gvm install groovy 2.2.2
```

#### 安装：方式一、scoop

```ps1
> scoop install groovy
> groovy -v
Groovy Version: 4.0.22 JVM: 11.0.20 Vendor: Oracle Corporation OS: Windows 11
```

### 目录

```bash
bin/groovy.bat # 执行 groovy
bin/groovyc.bat # 编译 todo
bin/groovysh.bat # 执行 todo
doc/ # todo
```

## GDK

GDK（Groovy JDK） 扩展了 JDK。

如调用系统命令的方式：

:::::: tabs

@tab JDK

```java
try {
  Process process = Runtime.getRuntime().exec("git help");
  BufferReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
  String line;
  while((line = result.readLine()) != null) {
    System.out.println(line);
  }
} catch (IOException e) {
  e.printStackTrace();
}
```

@tab GDK

```groovy
println "git help".execute().text
```

::::::

## 语法

### 变量：字符串

```groovy
// 定义变量 - 字符串
def str = 'hello world'     // 纯字符串
def str = '''hello
world'''                    // 允许多行
def str = "Hello ${str}"    // 允许插值
def str = """Hello
${str}
World!"""                   // 允许多行 + 允许插值

// 定义变量 - 正则
def regex = /hello/
```

### 变量：数值

```groovy
// 定义变量 - 数值
def // 自动类型识别

// def
def b = Integer.MAX_VALUE // 2147483647
assert b instanceof Integer
def c = b + 1 // -2147483648
// def c = 2147483647 + 1 // -2147483648
assert c instanceof Integer
def l = 2147483647 // Integer.MAX_VALUE + 1
assert l instanceof Long

// 二进制 0b
assert 0b1000 == 8
// 八进制 0
assert 077 == 63
// 十六进制
assert 0x77 == 119

// 科学计数法
assert 1e3 == 1_000.0 // 1 x 10^3 // Groovy 支持 _ 下划线对数字进行分割，使得数值易于识别
assert 2E4 == 20_000.0 // 2 x 10^4
assert 3e+1 == 30.0
assert 4E-2 == 0.04
assert 5e-1 == 0.5
```

```groovy
// 显式指定类型
byte
char
short
int
long
java.math.BigInteger // 计算精度

int a = 1

// 通过后缀指定类型
G/g BigDecimal
G/g BigInteger
L/l Long
I/i Integer
D/d Double
F/f Float

def a = 42I
```

### 变量：集合

```groovy
// 定义
def numbers = [1, 2, 3]
assert numbers instanceof List // 默认使用 java.util.ArrayList
assert numbers.size() == 3

def heterogeneous = [1, "a", true] // 可放入不同类型元素

def linkedList = [2, 3, 4] as LinkedList // 显式指定类型
LinkedList otherLinked = [3, 4, 5]
assert linkedList instanceof java.util.LinkedList

def numArr = [1, 2, 3] as int[] // 显式指定为数组
String[] arrStr = ["Ananas", "Banana", "Kiwi"]
assert arrStr instanceof String[]
assert !(arrStr instanceof List)

// 访问
def letters = ['a', 'b', 'c', 'd']
assert letters[0] == 'a'
// 遍历
String[] groovyBooks = ['Groovy in Action', 'Making Java Groovy']
assert groovyBooks.every{ it.contains('Groovy') }

// 添加
letters << 'e' // 末尾添加
assert letters[ 4] == 'e'
assert letters[-1] == 'e'
```

### 变量：Map

::: info

Groovy 的 map 对象就是 LinkedHashMap 实例

:::

```groovy
def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']
assert colors['red'] == '#FF0000'
assert colors.green == '#00FF00'

colors['pink'] = '#FF00FF'
colors.yellow = '#FFFF00'
```

### 定义：方法

```groovy
// 定义方法
def 'abstract'() { true }
```

### todo

todo https://www.bilibili.com/video/BV1214y1r76z

todo 关系运算、逻辑运算、位运算、对象运算、正则

todo 类、内部类、继承（extend）、抽象类（abstract）、接口（interface/implements）

todo trait

### 定义：类

```groovy
class Person {
    String name
    Integer age
}
Person.metaClass.sex = "女" // 动态添加 sex 属性
Person.metaClass.setNameUpperCase = {->name.toUpperCase()} // 动态添加方法
Person.metaClass.static.setNameLowerCase = {String name -> name.toLowerCase()} // 动态添加静态方法

def p = new Person(name:"xx",age:19)
println p.sex
println p.setNameUpperCase()
println Person.setNameLowerCase("ABC")
```

### 定义：闭包（Closure）

```groovy
def pickEven(n, block) {
    for(int i=2; i<=n; i+=2) {
        block(i); // 指向闭包的调用
    }
}
pickEven(10, {println it}); // {} 匿名代码块
```

变量

- `this` —— todo
- `owner`
- `delegate`

```groovy
def c1 = {
    println "c1-this:" + this
    println "c1-owner:" + owner
    println "c1-delegate:" + delegate
}
c1.call()
```

### 处理：json

自带 json 处理

```groovy
// to json
def p = new Person(name:"lucy",age:19)
println JsonOutput.toJson(p)
println JsonOutput.prettyPrint(JsonOutput.toJson(p))

// from json
def str = '{"age":19,"name":"lucy"}'
def js = new JsonSlurper()
def p2 = (Person)(js.parseText(str))
println p2
```

三方 json 处理 （gson）

```groovy
import com.google.gson.Gson

// to json
def p = new Person(name:"lucy",age:19)
def gson = new Gson()
println gson.toJson(p)

// from json
def str = '{"age":19,"name":"lucy"}'
def p2 = gson.fromJson(str, Person.class)
println p2
```

### 处理：xml

```groovy
final String xml = '''
<students>
  <student id="1">
    <name>张三</name>
    <age>18</age>
    <sex>男</sex>
    <score>98</score>
  </student>
  <student id="2">
    <name>李四</name>
    <age>21</age>
    <sex>女</sex>
    <score>93</score>
  </student>
</students>
'''

// from xml
def xs = new XmlSlurper()
def students = xs.parseText(xml)
println students.student[0].name.text() // 获取节点值
println students.student[1].@id // 获取属性值

// to xml
import groovy.xml.MarkupBuilder
def s = new StringWriter();
def mb = new MarkupBuilder(s)
mb.students() { // 💡看上去像方法，但并非方法，只是语法相似
  student(id:'1') { // (prop:value) 传入属性值
    // {} 传入节点
    name(a:'a', '张三') // (prop:value,...,content) 传入节点内容
    age(18)
    // ...
  }
}
println s
```

### 处理：文件

读写

```groovy
def file = new File("d://students.xml")
println file.getText()
println '---------------'
file.eachLine { println it}
println '---------------'
file.withReader {
    char[] buffer = new char[100]
    it.read(buffer)
    return buffer
}
println '---------------'
// 复制文件 （❗生产直接用 cp 命令）
def copy(String srcPath, String descPath) {
    def descFile = new File(descPath)
    if (!descFile.exists()) {
        descFile.createNewFile()
    }
    new File(srcPath).withReader {
        def lines = it.readLines()
        descFile.withWriter {
            lines.each { line ->
                it.append(line + "\r\n")
            }
        }
    }
    return true
}
println copy("d://students.xml", "d://students-copy.xml")
```

对象序列化

```groovy
def saveObject(Object obj, String path) {
    def file = new File(path)
    if(!file.exists()) {
        file.createNewFile()
    }
    file.withObjectOutputStream {
        it.writeObject(obj)
    }
    return true
}
def readObject(String path) {
    def obj = null
    def file = new File(path)
    if (file == null || !file.exists()) {
        return null
    }
    file.withObjectInputStream {
        obj = it.readObject()
    }
    return obj
}
class Student implements Serializable { // 💡实现序列化接口
    String name
    Integer age
}
def s = new Student(name:'luck', age:18)
// 写
saveObject(s, "d://demo.txt")
// 读
def s2 = (Student) readObject("d://demo.txt")
println s2.name
```

## ~~Maven 项目~~

都用 Groovy 了，就直接 Gradle 走起！

```xml title="pom.xml"
<dependencies>
    <dependency>
        <groupId>org.apache.groovy</groupId>
        <artifactId>groovy-all</artifactId>
        <version>4.0.14</version>
        <type>pom</type>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.gmavenplus</groupId>
            <artifactId>gmavenplus-plugin</artifactId>
            <version>1.13.1</version>
            <executions>
                <execution>
                    <goals>
                        <goal>execute</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>org.apache.groovy</groupId>
                    <artifactId>groovy</artifactId>
                    <version>4.0.14</version>
                    <scope>runtime</scope>
                </dependency>
            </dependencies>
            <configuration>
                <scripts>
                    <script>src/main/groovy/Main.groovy</script>
                </scripts>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Gradle 项目

相比 Maven Xml 配置，Gradle 配置（Groovy/Kotlin 语法）更易于扩展。

常见场景： Android, Java 开发

[link](../dev-java-maven/gradle.md)

## Java 调用

todo https://www.bilibili.com/video/BV1cj41117c2/
