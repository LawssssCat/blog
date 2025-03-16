package org.example.feature.enhanceSwitch;

import org.junit.jupiter.api.Test;

public class YieldTest {
    @Test
    void test() {
        System.out.println("doSome(3) = " + doSome(3));;
    }

    private String doSome(int number) {
        var value = switch (number) {
            case 0 -> "no gifts";
            case 1 ->{yield "only one gift";}
            default -> {
                if (number < 0) {
                    yield "no gifts";
                } else {
                    yield number + " gifts";
                }
            }
        };
        return value;
    }
}
