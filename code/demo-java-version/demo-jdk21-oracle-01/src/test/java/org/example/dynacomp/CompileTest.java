package org.example.dynacomp;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompileTest {
    @Test
    void test() throws IllegalAccessException, InstantiationException {
        String fullName = "DynaClass";
        String src = """
            public class DynaClass {
              public String toString() {
                return "Hello, I am" + this.getClass().getSimpleName();
              }
            }
            """;

        log.info("compile content: {}", src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance = de.javaCodeToObject(fullName, src.toString());
        log.info("compile result: {}", instance);
    }

}
