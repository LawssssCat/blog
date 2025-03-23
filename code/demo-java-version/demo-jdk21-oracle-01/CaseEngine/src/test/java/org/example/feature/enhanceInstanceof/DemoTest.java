package org.example.feature.enhanceInstanceof;

import org.junit.jupiter.api.Test;

public class DemoTest {
    @Test
    void test() {
        doSome("str");
    }

    private void doSome(Object obj) {
        if (obj instanceof String) {
            String value = (String) obj;
            if ("str".equals(value)) {
                System.out.println("value = " + value);
            }
        }
        if (obj instanceof String value && "str".equals(value)) { // 末尾加上变量名
            System.out.println("value = " + value);
        }
    }
}
