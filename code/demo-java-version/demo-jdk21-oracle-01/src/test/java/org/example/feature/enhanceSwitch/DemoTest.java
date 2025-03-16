package org.example.feature.enhanceSwitch;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class DemoTest {
    private static final Logger log = Logger.getLogger(DemoTest.class.getName());

    @Test
    void test() {
        // 基础类型值判断
        doSome(1);
        hr();
        // 类型判断
        doSome2(null); // hit ~ null
        doSome2(new Data(new GeoLocation(1.1, 2.2))); // hit ~ double
        doSome2(new MyClass("hello")); // hit ~ myclass
        doSome2(1.1); // default
        hr();
    }

    @lombok.Data
    @AllArgsConstructor
    static class MyClass {
        String name;
    }

    record Data(GeoLocation geo) {

    }

    record GeoLocation(double lng, double lat) {
    }

    private void doSome2(Object value) {
        switch (value) {
            case null -> log.info("null");
//            case Data d -> {
//                if (d.geo != null) {
//                    log.info("d.lng() = " + d.geo.lng());
//                    log.info("d.lat() = " + d.geo.lat());
//                }
//            }
            case Data(GeoLocation(var x, double y)) -> { // 1.记录类型允许嵌套取值；2.不需要显式指定类型；3.变量名无需一致，因为值根据位置匹配的
                log.info("d:lng = " + x);
                log.info("d:lat = " + y);
            }
            case MyClass myClass -> log.info("myClass.name = " + myClass.getName()); // 非记录模式，不能结构，乖乖用get
            case Integer i -> log.info("i = " + i);
            default -> {
                log.info("default:value = " + value);
            }
        }
    }

    private void doSome(int flag) {
        switch (flag) {
            case 1 -> {
                log.info("1:flag = " + flag);
                flag++;
                // 无需 break
            }
            case 2 -> log.info("2:flag = " + flag); // 单行无需 {...}
            // 可以不写default分支
        }
        log.info("e:flag = " + flag);

        Object obj = null;
        var formatted = switch (obj) {
//            case Integer i  when i > 1-> String.format("int %d > 1", i);
            case Integer i -> String.format("int %d", i);
            case DemoTest i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            case null, default        -> "unknown"; // obj.toString();
        };

//        String s = null;
//        switch (s) {
//            case "Foo", "Bar" -> log.info("Great");
//            default           -> log.info("Ok");
//        }
    }

    private void hr() {
        log.info("======================");
    }
}
