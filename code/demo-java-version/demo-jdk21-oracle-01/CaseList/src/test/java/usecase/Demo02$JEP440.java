package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP440 {
    // @AllArgsConstructor
    // @Getter
    // public static class Point {
    // private Object x;
    // private Object y;
    // }

    public void test() throws IllegalAccessException {
        // var p = new Point(1, 2);
        // if (p instanceof Point point) {
        // int x = (int)point.getX();
        // int y = (int)point.getY();
        // log.info("x={}, y={}", x, y);
        // }
        record Point(Object x, Object y) {
        }
        var p = new Point(1, 2);
        if (p instanceof Point(Integer x, Integer y)) {
            log.info("x={}, y={}", x, y);
        }
    }
}
