package org.example.dynacomp;

import java.nio.file.Files;
import java.nio.file.Path;

import org.example.utils.CaseExecutor;
import org.junit.jupiter.api.Assertions;
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
              log.info("invoke method");
              }
            }
            """;
        doTestStr(src, fullName);
    }

    private static void doTestStr(String src, String fullClassName) throws Exception {
        CaseExecutor caseExecutor = new CaseExecutor(fullClassName, src,
            classMeta -> {
                int maxCount = 2;
                int num = classMeta.parsedMethod().getLineNumberTable().getTableLength();
                Assertions.assertTrue(num <= maxCount, "行数" + num + "应该小于" + maxCount + "，以减少圈复杂度");
            });
        caseExecutor.check();
    }
}
