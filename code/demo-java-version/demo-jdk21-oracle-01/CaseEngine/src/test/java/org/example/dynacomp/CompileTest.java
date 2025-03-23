package org.example.dynacomp;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import org.example.utils.AssertUtils;
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
              static {
                log.info("static block invoke by {}", DynaClass.class.getClassLoader());
              }
              public String toString() {
                return "Hello, I am" + this.getClass().getSimpleName();
              }
              public void test() {
              log.info("invoke method"); }
            }
            """;
        doTestStr(src, fullName);
    }

    private static void doTestStr(String src, String fullClassName) throws Exception {
        log.info("compile content: {}", src);
        DynamicEngine engine = DynamicEngine.getInstance();
        byte[] bytes = engine.javaCodeToClassBytes(fullClassName, src);
        Class clazz = engine.getClassLoader().loadClass(fullClassName, bytes);
        Object instance = clazz.newInstance();
        log.info("compile result: {}", instance);
        Method method = instance.getClass().getMethod("test");
        method.invoke(instance);
        log.info("method test Number");
        AssertUtils.assertMethodLineLess(bytes, method, 10);
    }
}
