package org.example.feature.recordtype;

import org.junit.jupiter.api.Test;

public class DemoTest {
    record GeoLocation(double lng, double lat) {
    }

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
            record Obj2(double v) {

            }
        }
        Obj1 o1 = new Obj1(new Obj1.Obj2(1));
        Obj1 o2 = new Obj1(new Obj1.Obj2(1));
        System.out.println("(o1.equals(o2)) = " + (o1.equals(o2))); // true 嵌套值相等
        // 数组
        var numbers = new Integer[] {1, 2, 3};
        switch (numbers) {
            case Integer[] arr when arr.length >= 3 -> {
                System.out.println("The array contains three elements.");
            }
            default -> throw new IllegalStateException("Unexpected value: " + numbers);
        }
        // 重叠
        Object x = 3;
        switch(x) {
            case String s when s.length() > 3 -> {
                System.out.println("s = " + s);
            }
            default -> {}// throw new IllegalStateException("Unexpected value: " + x);
        }
    }
}
