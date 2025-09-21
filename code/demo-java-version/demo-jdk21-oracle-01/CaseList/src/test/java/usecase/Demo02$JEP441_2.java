package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP441_2 {
    Object obj = null;

    public void test() {
        String formatted = switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s when "xx".equals(obj) -> String.format("String %s", s); // 卫语句
            default -> "unknown";
            // case null, default -> "unknown";
        };
        log.info("result: {}", formatted);
    }
}
