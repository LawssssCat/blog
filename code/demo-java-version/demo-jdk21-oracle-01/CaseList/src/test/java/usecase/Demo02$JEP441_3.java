package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP441_3 {
    Object obj = null;

    public void test() {
        String formatted = switch (obj) {
            case String s -> String.format("String %d", s);
            case String s when "xx".equals(obj) -> String.format("String1 %s", s); // 卫语句
            case null, default -> "unknown";
        };
        log.info("result: {}", formatted);
    }
}
