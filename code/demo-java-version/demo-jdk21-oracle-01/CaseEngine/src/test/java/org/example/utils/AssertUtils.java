package org.example.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Assertions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertUtils {
    public static void assertMethodLineLess(byte[] classData, java.lang.reflect.Method method, int maxCount) throws IOException {
        ClassParser classParser = toClassParser(classData);
        JavaClass parsedClass = classParser.parse();
        // log.debug("pase success {}", parsedClass);
        Method parsedMethod = parsedClass.getMethod(method);
        log.debug("find method: {}", parsedMethod);
        log.debug("method line: {}", parsedMethod.getLineNumberTable());
        int num = parsedMethod.getLineNumberTable().getTableLength();
        Assertions.assertTrue(num < maxCount);
    }

    private static ClassParser toClassParser(byte[] classData) {
        InputStream is = new ByteArrayInputStream(classData);
        return new ClassParser(is, "ignore");
    }
}
