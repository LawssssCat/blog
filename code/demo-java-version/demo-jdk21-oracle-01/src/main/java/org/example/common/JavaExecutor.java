package org.example.common;

import lombok.extern.slf4j.Slf4j;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;

/**
 * JDK6 动态编译：
 *
 * <a href=https://docs.oracle.com/en/java/javase/11/docs/api/java.compiler/javax/tools/Tool.html#run(java.io.InputStream,java.io.OutputStream,java.io.OutputStream,java.lang.String...)>
 *     run(java.io.InputStream,java.io.OutputStream,java.io.OutputStream,java.lang.String...)
 * </a>
 */
@Slf4j
public class JavaExecutor {
    private static String BASE_PATH;

    static {
        log.info("load JavaExecutor");
        BASE_PATH = System.getProperty("user.dir");
        log.debug("BASE_PATH = {}", BASE_PATH);
    }

    public JavaExecutor() {

    }

    public static void main(String[] args) {
        //获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //获取Java文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        //定义要编译的源文件
        String javPath = BASE_PATH + "/demo/src/Main.java";
        log.info("compile: {}", javPath);
        File file = new File(javPath);
        //通过源文件获取到要编译的Java类源码迭代器，包括所有内部类，其中每个类都是一个 JavaFileObject，也被称为一个汇编单元
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(file);
        //生成编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        //执行编译任务
        task.call();
    }
}
