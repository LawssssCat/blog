package org.example.utils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaseUtils {
    public static void doTest(String className, Consumer<CaseExecutor.ClassMeta>... checkList) {
        doTest(className, new Object[0], checkList);
    }

    @SneakyThrows
    public static void doTest(String className, Object[] args, Consumer<CaseExecutor.ClassMeta>... checkList) {
        String pathStr = className.replace(".", "/") + ".java";
        Path path = Path.of("../CaseList/src/test/java", pathStr).toAbsolutePath();
        log.info("test: {}", path);
        CaseExecutor caseExecutor = new CaseExecutor(className, path, checkList);
        caseExecutor.check(args);
    }

    public static Consumer<CaseExecutor.ClassMeta> assertMethodLineMax(int maxCount) {
        return classMeta -> {
            int num = classMeta.parsedMethod().getLineNumberTable().getTableLength();
            Assertions.assertThat(num)
                .as("方法行数" + num + "应该小于" + maxCount + "，以减少圈复杂度")
                .isLessThanOrEqualTo(maxCount);
        };
    }

    public static Consumer<CaseExecutor.ClassMeta> assertClassLineMax(int maxCount) {
        return classMeta -> {
            int num = getClassLine(classMeta);
            Assertions.assertThat(num)
                .as("类的行数" + num + "应该小于" + maxCount + "，以减少圈复杂度")
                .isLessThanOrEqualTo(maxCount);
        };
    }

    private static int getClassLine(CaseExecutor.ClassMeta classMeta) {
        String src = classMeta.source();
        String[] split = src.split("\n");
        return (int)Arrays.stream(split)
            .filter(str -> !str.trim().startsWith("//"))
            .filter(str -> !str.isBlank())
            .count();
    }
}
