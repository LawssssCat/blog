---
title: Apache POI 使用
date: 2024-07-02
tag:
  - java
  - office
  - excel
order: 33
---

Apache POI 是一个流行的 Java 库，用于处理 Microsoft Office 格式文件，包括 Excel、Word 和 PowerPoint。它提供了丰富的 API，可以创建、读取和修改各种类型的 Office 文档。

官网： <https://poi.apache.org/>

优点：

- 功能强大：支持处理复杂的 Excel 文件，包括单元格、样式、图表等内容。
- 稳定性高：作为一个成熟的开源项目，得到广泛支持和持续维护。
- 灵活性：可以满足各种定制化需求，可以实现复杂的 Excel 处理功能。

缺点：

- 性能相对较低：在处理大量数据时，性能可能受到一定影响。

<!-- more -->

## 引入依赖

::: tabs#excel_version

@tab xls 03

```xml
<!-- xls 03 -->
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi</artifactId>
  <version>3.9</version>
</dependency>
<!--日期-->
<dependency>
    <groupId>joda-time</groupId>
    <artifactId>joda-time</artifactId>
    <version>2.10.10</version>
</dependency>
```

@tab xlsx 07

```xml
<!-- xlsx 07 -->
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi-ooxml</artifactId>
  <version>3.9</version>
</dependency>
```

:::

## 版本对比

- HSSFWorkbook
  - 03 版本，最大 65536 行
  - 主要使用 HSSFWorkbook、HSSFSheet、HSSFRow、HSSFCell 等对象来操作 Excel 文件
- XSSFWorkbook
  - 07 版本，没有行限制，但是相对慢
  - 主要使用 XSSFWorkbook、XSSFSheet、XSSFRow、XSSFCell 等对象来操作 Excel 文件
- SXSSFWorkbook
  - 没有行限制，写数据快，占用内存少
    - 实现 “BigGridDemo” 策略的流式版本，允许写入大文件而不耗尽内存，因为任何时候只有可配置的行部分被保存在内存中
    - 👆 请注意，依然可能会消耗大量内存，这些内存基于正在使用的功能，例如合并区域、注释。。。仍然只存储在内存中，因此如果广泛使用，可能需要大量内存

## 写 excel

:::::: tabs#excel_version

@tab xls 03

```java
public class AppTest
{
    String filepath="E:\\xiezhrspace\\excel-demo\\fileoutput\\";
    @Test
    public void poiexcel03Test() throws  Exception{
        //1、创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //2、创建一个工作表
        Sheet sheet = workbook.createSheet("第一个工作表");

        //3、创建一行
        //3.1 创建第一行
        Row row1 = sheet.createRow(0);

        //4 创建一个单元格
        //4.1 创建第一行第一个单元格
        Cell cell11 = row1.createCell(0);

        // 5 设置单元格的值
        //5.1 设置第一行第一个单元格
        cell11.setCellValue("Hello World");

        FileOutputStream fileOutputStream = new FileOutputStream(filepath + "poiexcel03.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
}
```

@tab xlsx 07

07 版本操作与 03 版本操作基本没什么变化，我们只需将 03 版本代码中 `new HSSFWorkbook()` 修改成 `new XSSFWorkbook()` 即可

```java
 Workbook workbook = new XSSFWorkbook();
 ...省略
 FileOutputStream fileOutputStream = new FileOutputStream(filepath + "poiexcel07.xlsx");
```

::::::

## 写 excel （大文件）

:::::: tabs#excel_version

@tab xls 03

根据记录时间，耗时：1.663 秒

::: warning
如果 03 版本写入数据行数超过 65536 行会报错误： `java.lang.IllegalArgumentException: Invalid row number (65536) outside allowable range (0..65535)`
:::

```java
@Test
public void testBigDateExcelTest() throws Exception {
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("大文件导出测试");

    long begin = System.currentTimeMillis();

    for (int rowNum = 0; rowNum <65536 ; rowNum++) {
        Row row = sheet.createRow(rowNum);
        for (int cellNum = 0; cellNum < 10 ; cellNum++) {
            Cell cell = row.createCell(cellNum);
            cell.setCellValue("("+(rowNum+1) + "," + (cellNum+1)+")");
        }

    }
    FileOutputStream fileOutputStream = new FileOutputStream(filepath + "03版本批量写入.xls");
    workbook.write(fileOutputStream);
    fileOutputStream.close();
    workbook.close();

    long end = System.currentTimeMillis();
    System.out.println("耗时："+(double)(end-begin)/1000+"秒");
}
```

@tab xlsx 07

由于 07 及以上版本，没有限制行数，但写入数据时耗时相比较长。共耗时：10.959 秒

```java
Workbook workbook = new XSSFWorkbook(); // 将 new HSSFWorkbook() 修改成 new XSSFWorkbook() 即可
 ...省略
FileOutputStream fileOutputStream = new FileOutputStream(filepath + "07版本批量写入.xlsx");
```

::::::

使用 `SXSSFWorkbook` 优化导出： 通过建立中间缓存文件优化导出速度

```java
Workbook workbook = new SXSSFWorkbook(); // 将 new HSSFWorkbook() 修改成 new SXSSFWorkbook()
 ...省略
FileOutputStream fileOutputStream = new FileOutputStream(filepath + "07版本批量写入.xlsx");
workbook.write(fileOutputStream);
((SXSSFWorkbook) workbook).dispose(); // ❗写完后，清理临时文件，将数据写入过程中产生的缓存文件删除
```

使用 `SXSSFWorkbook` 导出耗时：10.959 秒

## 读 excel

todo https://www.cnblogs.com/Chary/p/18112420
