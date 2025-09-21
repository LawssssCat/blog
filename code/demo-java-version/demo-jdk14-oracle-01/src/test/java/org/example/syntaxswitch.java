package org.example;

import org.junit.jupiter.api.Test;

public class syntaxswitch {
    @Test
    void test() {
        doSome(1);
    }

    void doSome(int o) {
        Object v = switch (o) {
            case 1 -> {
                yield "1";
            }
            case 2 -> 2;
            default -> "default";
        };

        System.out.println("v = " + v);
    }
}
