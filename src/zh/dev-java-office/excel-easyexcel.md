---
title: EasyExcel 使用
date: 2024-07-02
tag:
  - java
  - office
  - excel
order: 36
---

EasyExcel 是一个阿里巴巴基于 Apache POI 封装的开源框架，专注于 Excel 文件的读写操作。它提供了简洁易用的 API，简化了 Excel 处理的流程。

官网：https://easyexcel.opensource.alibaba.com/

优点：

- 高性能：在处理大量数据时具有较高的性能，能够快速导入导出 Excel 文件。
- 支持注解：支持使用注解配置 Excel 文件的导入导出规则，简化了开发过程。

缺点：

- 功能相对有限：相比 Apache POI，功能相对简单，可能无法满足某些复杂的 Excel 处理需求。
- 定制化能力较弱：定制化能力不如 Apache POI 灵活。

<!-- more -->

**流式、内存开销小**

| Excel 解析流程 | XLSX 文件解压缩 | SAX 模式读 XML 文件 | 模型转换          | 返回调用者     |
| -------------- | --------------- | ------------------- | ----------------- | -------------- |
| POI            | 内存            | 内存                | WorkBook Row Cell | 全部内容       |
| EasyExcel      | 磁盘            | 磁盘                | Java 模型         | 一行、一行返回 |

## Demo

依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.3.2</version>
</dependency>
```

数据对象

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @ExcelProperty("员工编号")
    private  Integer id;
    @ExcelProperty("员工姓名")
    private  String name;
    @ExcelProperty("员工年龄")
    private  Integer age;
    @ExcelProperty("员工薪资")
    private  Double salary;
    @ExcelProperty("入职日期")
    private Date inDate;
}
```

### 写入 excel

::: tabs

@tab 测试数据

```java
private List<Employee> data(long count) {
    List<Employee> list = ListUtils.newArrayList();
    for (int i = 0; i < count; i++) {
        Employee employee = new Employee();
        employee.setId(i);
        employee.setName("小陈"+i);
        employee.setAge(18+1);
        employee.setSalary(6.66+i);
        employee.setInDate(new Date());
        list.add(employee);
    }
    return list;
}
```

@tab 写入数据

```java
@Test
public void testbaseWrite() {
    String fileName = "E:\\xiezhrspace\\excel-demo\\fileoutput\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
    // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
    // 如果这里想使用03 则 传入excelType参数即可
    EasyExcel.write(fileName, Employee.class).sheet("测试模板").doWrite(data(10));
}
```

:::

### 读出 excel

todo https://www.cnblogs.com/Chary/p/18112420

### 复杂表头

todo https://www.cnblogs.com/Chary/p/18112420

### 重复多次写入

```java
@Test
public void testmanyDateWriter(){
    // 方法1: 如果写到同一个sheet
    String fileName = "E:\\xiezhrspace\\excel-demo\\fileoutput\\" + "manydataWrite" + System.currentTimeMillis() + ".xlsx";
    // 这里 需要指定写用哪个class去写
    try (ExcelWriter excelWriter = EasyExcel.write(fileName, Employee.class).build()) {
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
        for (int i = 0; i < 5; i++) {
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            List<Employee> data = data(200000);
            excelWriter.write(data, writeSheet);
        }
    }

    // 方法2: 如果写到不同的sheet 同一个对象
    fileName =  "E:\\xiezhrspace\\excel-demo\\fileoutput\\" + "manydataWrite" + System.currentTimeMillis() + ".xlsx";
    // 这里 指定文件
    try (ExcelWriter excelWriter = EasyExcel.write(fileName, Employee.class).build()) {
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            List<Employee> data = data(200000);
            excelWriter.write(data, writeSheet);
        }
    }

    // 方法3 如果写到不同的sheet 不同的对象
    fileName =  "E:\\xiezhrspace\\excel-demo\\fileoutput\\" + "manydataWrite" + System.currentTimeMillis() + ".xlsx";
    // 这里 指定文件
    try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class
            // 实际上可以一直变
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(Employee.class).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            List<Employee> data = data(200000);
            excelWriter.write(data, writeSheet);
        }
    }
}
```

### excel 填充

todo https://www.cnblogs.com/Chary/p/18112420

### excel 样式

todo
