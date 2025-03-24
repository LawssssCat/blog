package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP441 {
    public void test() {
        Object obj = "xx";
        String formatted = "unknown";
        if (obj instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (obj instanceof String s) {
            if ("xx".equals(obj)) { // 判断
                formatted = String.format("String %s", s);
            }
        }
        // String formatted = switch (obj) {
        // case Integer i -> String.format("int %d", i);
        // case Long l -> String.format("long %d", l);
        // case Double d -> String.format("double %f", d);
        // case String s when "xx".equals(obj) -> String.format("String %s", s); // 卫语句
        // default -> "unknown";
        // };
        log.info("result: {}", formatted);
    }
}
