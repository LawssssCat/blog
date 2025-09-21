package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP361 {
    int day = 1;

    public void test() throws IllegalAccessException {
        int result;
        switch (day) {
            case 1:
            case 2:
            case 3:
                result = 1;
                break;
            case 4:
                result = 2;
                break;
            case 5:
            case 6:
                result = 3;
                break;
            case 7:
                result = 4;
                break;
            default:
                var a = 1 & 2;
                throw new IllegalAccessException("Wat: " + day);
        }
        // int result = switch (day) { // 直接返回
        // case 1, 2, 3 -> 1; // 多条件合并
        // case 4 -> 2; // lambda一行
        // case 5, 6 -> 3;
        // case 7 -> 4;
        // default -> throw new IllegalAccessException("Wat: " + day); // 默认情况
        // };
        log.info("result: {}", result);
    }
}
