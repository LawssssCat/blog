package org.example.dynacomp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompileTest {
    @Test
    @SneakyThrows
    void testFile() {
        String userDir = System.getProperty("user.dir");
        Path file = Path.of(userDir, "../CaseList/src/test/java/DemoTest.java");
        log.info("source path: {}", file);
        String fullName = "DemoTest";
        String src = Files.readString(file);
        doTestStr(src, fullName);
    }

    @Test
    @SneakyThrows
    void testStr() {
        String fullName = "aa.bb.cc.DynaClass";
        String src = """
            package aa.bb.cc;
            import lombok.extern.slf4j.Slf4j;
            @Slf4j
            public class DynaClass {
              public String toString() {
                return "Hello, I am" + this.getClass().getSimpleName();
              }
              public void test() {
                log.info("invoke method");
              }
            }
            """;
        doTestStr(src, fullName);
    }

    private static void doTestStr(String src, String fullName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        log.info("compile content: {}", src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance = de.javaCodeToObject(fullName, src);
        log.info("compile result: {}", instance);
        Method method = instance.getClass().getMethod("test");
        method.invoke(instance);
    }
}
