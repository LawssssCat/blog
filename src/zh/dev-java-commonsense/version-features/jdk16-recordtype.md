---
title: JDK16功能特性：记录类型
date: 2025-03-15
order: 99
---

## 是什么？

用来替代手写的Java“值对象”和让方法返回多个返回值。

### 作用一：（“值对象”特性）

“记录类型”通常用于表示“值对象/数据的容器”。

在“记录类型”之前，表示“值对象”方式有：

+ 手写 Java 类，实现类中的 get/set/equals/hashCode/toString 等方法
+ 手写 Java 类，使用 lombok/immutables 等第三方库在编译前/编译期间生成所需方法

```java
// lng、lat 称为该 record 类型的 “组件”
public record GeoLocation(double lng, double lat) {
  public GeoLocation(double lng, double lat) { // 重写构造器实现参数校验
    if (lng<=180 && lng >=-180) {
      this.lng = lng;
    } else {
      throw new IllegalArgumentException("Invalid value of longitude");
    }
    if (lat <= 90 && lat >= -90) {
      this.lat = lat;
    } else {
      throw new IllegalArgumentException("Invalid value of lattitude");
    }
  }
  // 简写
  // public GeoLocation() {
  //   if (!(lng<=180 && lng >=-180)) {
  //     throw new IllegalArgumentException("Invalid value of longitude");
  //   }
  //   if (!(lat <= 90 && lat >= -90)) {
  //     throw new IllegalArgumentException("Invalid value of lattitude");
  //   }
  // }
}
```

含义：

+ 每个“组件”都会有与之对应的 `private final` 字段
+ 自动有 `@AllArgsConstructor` 效果
+ 自动有 `@Getter` 效果
+ 自动有 `@EqualsAndHashCode` 效果
+ 自动有 `@ToString` 效果

> 实际就是继承了 `java.lang.Record` 的 Java 类

::: details 其他语言中的“记录类型”

```kotlin
data class GeoLocation (
  val lng: Double,
  val lat: Double
)
```

```scala
case class GeoLocation(lng: Double, lat: Double)
```

:::

### 作用二

可以使用“记录类型”来封装多个返回值来作为方法的返回，实现方法返回多个返回值的效果。

在“记录类型”之前，方法返回多个值需要通过 `List`/`Map` 或者通过封装的 `Pair<A,B>` 来实现。

### 作用三：（可嵌套特性）

表示实际的值，如领域模型（DDD）中的值对象或者如`GeoLocation`/`Position`的表示地理位置坐标的值

```java
public record Order(
  String orderId, 
  String userId, 
  LocalDateTime createdAt,
  List<LineItem> lineItems,
  Address deliveryAddress
) {
  public record LineItem(String productId, int quantity, BigDecimal price) {}
  public record Address(String addressLine, String cityId, String provinceId, String zipCode) {}
}
```

### 作用四：（可在方法中定义的特性）

记录类型可以定义在方法中，用来表示复杂的计算过程，方便复用和代码可读性（1、减少类的非功能性方法；2、在方法中显式指出了复杂计算的过程）

```java
record Range(int start, int end) {
  int distance() {
    return end - start;
  }
}
range r = new range(100, 200);
r.distance();
```

::: details others

```java
public class OrderCalculator {
  public Map<String, OrderSummary> calculate(List<Order> orders) {
    record OrderTotal(String orderId, BigDecimal total) {}

    Map<String, List<OrderTotal>> orderTotal = orders.stream()
      .collect(Collectors.groupingBy(Order::userId, Collectors.mapping(
        order -> {
          BigDecimal total = order.lineItems().stream()
            .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
          return new OrderTotal(order.orderId(), total);
        }
      ), Collectors.toList()));
    return orderTotal.entrySet().stream().map(entry -> new OrderSummary(
        entry.getKey(),
        entry.getValue().stream()
          .max(Comparator.comparing(OrderTotal::total))
          .map(OrderTotal::total).orElse(BigDecimal.ZERO)
      )
    ).collect(Collectors.toMap(OrderSummary::userId, Function.identity()));
  }
}
```

:::

### 细节

#### 相等、嵌套相等

```java
public class DemoTest {
    record GeoLocation(double lng, double lat) {}
    private GeoLocation doSome() {
        return new GeoLocation(1, 2);
    }
    
    @Test
    void test() {
        System.out.println("doSome() = " + doSome());
        System.out.println("(doSome() == doSome()) = " + (doSome() == doSome())); // false 不是同一个对象
        System.out.println("(doSome().equals(doSome())) = " + (doSome().equals(doSome()))); // true 值相同
        // 嵌套
        record Obj1(Obj2 o) {
            record Obj2(double v) {} 
        }
        Obj1 o1 = new Obj1(new Obj1.Obj2(1));
        Obj1 o2 = new Obj1(new Obj1.Obj2(1));
        System.out.println("(o1.equals(o2)) = " + (o1.equals(o2))); // true 嵌套值相等
    }
}
```

## 演进历史

JDK14

+ JEP 359: 预览特性

JDK15

+ JEP 384: 预览特性

JDK16

+ JEP 395: 正式功能

## 附录

### 参考

+ https://www.bilibili.com/video/BV1We4y1k727
