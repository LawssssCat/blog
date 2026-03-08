package org.example.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Functions
 * Suppliers
 * Predicates
 * ...
 */
public class FunctionalTest {
    @Test
    void testFunction() {
        // guava
        com.google.common.base.Function<String,String> funcGuava = new com.google.common.base.Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return "func-"+input;
            }
        };
        assertEquals("func-test", funcGuava.apply("test"));

        // jdk
        java.util.function.Function<String, String> funcJdk = new java.util.function.Function<String, String>() {
            @Override
            public String apply(String o) {
                return "func-"+o;
            }
        };
        assertEquals("func-test", funcJdk.apply("test"));
    }

    @Test
    void testFunction_Default() {
        Pair<String, String> pair = new Pair<>("hello", "world");

        // toString
        assertEquals(pair.toString(), Functions.toStringFunction().apply(pair));

        // compose —— 合成： 将 A 变成 B，再将 B 变成 C
        Function<Pair<String, String>, Integer> compose = Functions.compose(new Function<String, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable String input) { // B -> C
                return Optional.ofNullable(input).orElse("").length();
            }
        }, new Function<Pair<String, String>, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Pair<String, String> input) { // A -> B
                return input.getKey();
            }
        });
        assertEquals(pair.getKey().length(), compose.apply(pair));
    }
}
