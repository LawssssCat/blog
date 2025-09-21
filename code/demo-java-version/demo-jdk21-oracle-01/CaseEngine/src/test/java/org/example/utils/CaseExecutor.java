package org.example.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

import org.apache.bcel.classfile.*;
import org.example.dynacomp.DynamicEngine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaseExecutor {
    private String className;

    private Path path;

    private String src;

    private Consumer<ClassMeta>[] checkList;

    private ClassMeta classMeta;

    public record ClassMeta(
        String source,
        Class clazz,
        Method method,
        JavaClass parsedClass,
        org.apache.bcel.classfile.Method parsedMethod) {
    }

    public CaseExecutor(String className, Path path, Consumer<ClassMeta>... checkList) {
        this.className = className;
        this.path = path;
        this.checkList = checkList;
    }

    public CaseExecutor(String className, String src, Consumer<ClassMeta>... checkList) {
        this.className = className;
        this.src = src;
        this.checkList = checkList;
    }

    public void check(Object... args) throws Exception {
        doLoad(args);
        doCheck(args);
    }

    private void doLoad(Object... args) throws Exception {
        DynamicEngine engine = DynamicEngine.getInstance();
        String source = null;
        byte[] bytes;
        if (path != null) {
            source = Files.readString(path);
        } else {
            source = src;
        }
        if (source == null) {
            throw new RuntimeException();
        }
        bytes = engine.javaCodeToClassBytes(className, source);
        // 1
        InputStream is = new ByteArrayInputStream(bytes);
        ClassParser classParser = new ClassParser(is, "ignore");
        JavaClass parsedClass = classParser.parse();
        // log.debug("parsed class: {}", parsedClass);
        String className = parsedClass.getClassName();
        log.debug("classname: {}", className);
        // 2
        Class clazz = engine.getClassLoader().loadClass(className, bytes);
        for (Attribute attribute : parsedClass.getAttributes()) {
            if (attribute instanceof InnerClasses ic) {
                for (InnerClass innerClass : ic.getInnerClasses()) {
                    System.out.println("attribute = " + innerClass);
                    // engine.getClassLoader().loadClass(className, bytes, innerClass.getInnerClassIndex(), innerClass.getOuterClassIndex()
                    // - innerClass.getInnerClassIndex());
                }
            }
        }
        log.debug("compiled class: {}", clazz);
        Class[] types = Arrays.stream(args).map(arg -> arg.getClass()).toArray(Class[]::new);
        Method method = clazz.getMethod("test", types);
        log.debug("compiled method: {}", method);
        org.apache.bcel.classfile.Method parsedMethod = parsedClass.getMethod(method);
        log.debug("parsed method: {} - {}", parsedMethod, parsedMethod.getLineNumberTable());
        // 3
        this.classMeta = new ClassMeta(source, clazz, method, parsedClass, parsedMethod);
    }

    private void doCheck(Object... args) throws Exception {
        Object instance = this.classMeta.clazz().newInstance();
        this.classMeta.method().invoke(instance, args);
        for (int i = 0; i < checkList.length; i++) {
            checkList[i].accept(this.classMeta);
        }
    }
}
