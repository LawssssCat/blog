---
title: Spring Boot + Mybatis + H2 database 数据库
shortTitle: 整合 H2 数据库
date: 2024-04-06
category:
  - 示例项目
tag:
  - springboot
  - mybatis
  - h2database
---

整合 Spring Boot/Mybatis/H2 database 用来简单开发、测试。

<!-- more -->

todo mybatis 一级缓存、二级缓存

参考：

- <https://www.javaguides.net/2019/08/spring-boot-mybatis-crud-h2-database-example.html>

## H2 介绍

H2 由纯 Java 编写的开源关系数据库，可以直接嵌入到应用程序中，不受平台约束，便于测试。同时提供了 web 界面用于管理数据库。

H2 支持运行三种模式

- **ServerMode（传统模式）**：需要配置本地（或远程）数据库

  ```bash
  jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>
  jdbc:h2:tcp://localhost/~/test
  jdbc:h2:tcp://dbserv:8084/~/sample
  jdbc:h2:tcp://localhost/mem:test
  jdbc:h2:ssl://<server>[:<port>]/[<path>]<databaseName>
  jdbc:h2:ssl://localhost:8085/~/sample;
  jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=TRUE # 是否允许混合模式，即同时支持多个网络连接
  ```

- **Embedded（嵌入式）**：数据库连接关闭时，数据与表结构依然存在

  ```bash
  jdbc:h2:[file:][<path>]<databaseName>
  jdbc:h2:~/test # 示数据库存储在用户主目录中以 “test” 开头的文件中
  jdbc:h2:file:/data/sample # 支持绝对位置
  jdbc:h2:file:C:/data/sample (Windows only)
  jdbc:h2:file:/path/to/your/database;IFEXISTS=TRUE # 如果文件已经存在，则IFEXISTS=TRUE会避免尝试重新创建数据库而引发错误
  ```

- **In-Memory（内存模式）**：数据库连接关闭时，数据与表结构删除。多个线程可以访问同一个数据库，但是数据只能在同一个进程中可见。

  ```bash
  jdbc:h2:mem:
  jdbc:h2:mem:<databaseName>
  jdbc:h2:mem:test_mem # 打开一个名为 “test_mem” 的内存数据库
  jdbc:h2:tcp://localhost/mem:db1
  jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1 # 最后一个数据库连接关闭后，DB_CLOSE_DELAY=-1 让数据库不会被自动关闭，这样可保持数据在应用重启之间仍然可用
  jdbc:h2:file:/data/mydb;CIPHER=AES # 数据库加密（如果需要的话）
  ```

::: info
通过指定 url 方式指定运行模式。
更详细的 url 写法参考官网：
https://www.h2database.com/html/features.html#database_url
:::

## 整合

代码： <RepoLink path="/code/demo-springboot-h2/" />

### 项目初始化

导入依赖

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
  <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

配置

```yml title="application.yml"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/application.yml -->
```

数据库操作

::: tabs

@tab 数据库结构

```sql title="classpath:db/schema.sql"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/db/schema.sql -->
```

@tab 数据库数据

```sql title="classpath:db/data.sql"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/db/data.sql -->
```

:::

启动后通过 <http://localhost:9088/h2-console> 可以进入 web 管理界面。

### 数据库增删改查

::: tabs

@tab 模板类

```java title="model/Employee.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/model/Employee.java -->
```

@tab 数据库查询接口

```sql title="repository/EmployeeMapper.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/repository/EmployeeMapper.java -->
```

@tab 执行增删改查

```sql title="DemoH2SpringbootApplication.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/DemoH2SpringbootApplication.java -->
```

:::

### collection 级联查询

demo 类

::: tabs

@tab 市

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Urban {
    private int id;                   //市的编号
    private String cityId;            //省的编号（此博文没用到）
    private String urbanName;         //城市名字
    private List<School> schools;     //对应的所有的学校
    private List<Hospital> hospitals; //对应的所有的医院
}
```

@tab 学校

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private int id;               //学校编号
    private int urbanId;          //市的编号
    private String schoolName;    //学校名字
    private Long people;          //学校人数
}
```

@tab 医院

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    private int id;                 //医院编号
    private int urbanId;            //市的编号
    private String hospitalName;    //医院名称
    private Long people;            //医院人数
}
```

:::

级联查询

:::::: tabs

@tab java 级联

市

在市的 xml 中对学校和医院的 xml 进行一个调用（用 collection 中 select）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yh.mybatis.dao.mapper.UrbanMapper">
	  <resultMap id="findAllUrbanSandH" type="com.yh.mybatis.dao.pojo.Urban">
        <collection property="schools" javaType="java.util.List" ofType="com.yh.mybatis.dao.pojo.School"
                    select="com.yh.mybatis.dao.mapper.SchoolMapper.urbanSchool"
                    column="{urbanId=id}">
        </collection>
        <collection property="hospitals" javaType="java.util.List" ofType="com.yh.mybatis.dao.pojo.Hospital"
                    select="com.yh.mybatis.dao.mapper.HospitalMapper.findAllByUId"
                    column="{urbanId=id}">
        </collection>
    </resultMap>
<!--
		resultMap中的 <id><result>都可以不写，直接写List<School>和List<Hospital>
									type还是sql的返回类型
		collection中  property 是Urban中对应的字段
									javaType 是这个字段的类型
									ofType 是这个字段的泛型  这一项和上一项其实都可以不写，写上了看着更清晰
									select 是子表的按照市的编号查询所有数据的方法 这里要写下全路径
									column 作为select语句的参数传入, 也就是把市的编号id 传给医院和学校xml的urbanId
-->
		<select id="findAllUrbanSandH" resultMap="findAllUrbanSandH">
        select * from urban
    </select>
</mapper>
```

学校

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yh.mybatis.dao.mapper.SchoolMapper">
    <select id="urbanSchool" resultType="com.yh.mybatis.dao.pojo.School">
        select * from school where urban_id = #{urbanId}
    </select>
</mapper>
```

医院

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yh.mybatis.dao.mapper.HospitalMapper">
    <select id="findAllByUId" resultType="com.yh.mybatis.dao.pojo.Hospital">
        select * from hospital where urban_id = #{urbanId}
    </select>
<!--实际工作不建议用 *，id就是mapper接口中对应的方法名，resultType就是查询出结果后返回的list的泛型
 urban_id = #{urbanId} 按照urban_id去查找-->
</mapper>
```

@tab sql 级联

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yh.mybatis.dao.mapper.UrbanMapper">
		<resultMap id="findAllUrbanSandH2" type="com.yh.mybatis.dao.pojo.Urban">
        <id property="id" column="id"/>
        <result property="cityId" column="city_id"/>
        <result property="urbanName" column="urban_name"/>
<!--这上面这几个字段就是urban表中，自带的那几个字段-->
        <collection property="schools" javaType="java.util.List" ofType="com.yh.mybatis.dao.pojo.School">
            <id property="id" column="sid"/>
            <result property="urbanId" column="surban_id"/>
            <result property="schoolName" column="school_name"/>
            <result property="people" column="speople"/>
        </collection>
<!--这上面就是school表中的字段
		javaType是urban类中定义的school的类型  可以不写
		ofType就是泛型，这个还是很有必要的，接下来的id result 就是这个类中定义的各种字段，要写全
		如果涉及到的任何表中，在数据库中有重复的字段名，那就必须要起别名。（例如各个表中的id）
		起别名直接在下面的sql中就可以。
-->
        <collection property="hospitals" javaType="java.util.List" ofType="com.yh.mybatis.dao.pojo.Hospital">
            <id property="id" column="hid"/>
            <result property="urbanId" column="hurban_id"/>
            <result property="hospitalName" column="hospital_name"/>
            <result property="people" column="hpeople"/>
        </collection>
    </resultMap>
		<select id="findAllUrbanSandH2" resultMap="findAllUrbanSandH2">
        select  urban.city_id
                ,urban.id
                ,urban.urban_name
                ,school.id sid
                ,school.urban_id surban_id
                ,school.school_name
                ,school.people speople
                ,hospital.id hid
                ,hospital.urban_id hurban_id
                ,hospital.hospital_name
                ,hospital.people hpeople
        from urban
            inner join school on urban.id = school.urban_id
            inner join hospital on urban.id = hospital.urban_id
    </select>
</mapper>
```

::::::
