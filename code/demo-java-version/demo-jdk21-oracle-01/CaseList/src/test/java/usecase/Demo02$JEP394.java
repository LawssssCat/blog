package usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP394 {
    Object obj = "str";

    public void test() {
        int result = 0;
        if (obj instanceof String) {
            String value = (String)obj;
            if ("str".equals(value)) {
                result = 1;
            }
            // result = "str".equals((String)obj) ? 1 : result;
        }
        // 答案：
        // if (obj instanceof String value && "str".equals(value)) {
        // // 1.末尾加上变量名；
        // // 2.变量可以在“&&”判断中使用（❗即不可在“||”中使用）
        // result = 1;
        // }
        log.info("result: {}", result);
    }
}
