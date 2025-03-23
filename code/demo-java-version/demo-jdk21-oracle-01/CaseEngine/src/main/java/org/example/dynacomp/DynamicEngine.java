package org.example.dynacomp;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import javax.tools.*;

import org.example.Main;

/**
 * JDK6 动态编译：
 *
 * <a
 * href=https://docs.oracle.com/en/java/javase/11/docs/api/java.compiler/javax/tools/Tool.html#run(java.io.InputStream,java.io.OutputStream,java.io.OutputStream,java.lang.String...)>
 * run(java.io.InputStream,java.io.OutputStream,java.io.OutputStream,java.lang.String...)
 * </a>
 * https://seanwangjs.github.io/2018/03/13/java-runtime-compile.html
 */
public class DynamicEngine {
    private static DynamicEngine ourInstance = new DynamicEngine();

    public static DynamicEngine getInstance() {
        return ourInstance;
    }

    private ClassLoader parentClassLoader;

    private String classpath;

    private DynamicEngine() {
        this.parentClassLoader = Main.class.getClassLoader();
        this.buildClassPath(); // 加载依赖
    }

    private void buildClassPath() {
        StringBuilder sb = new StringBuilder(System.getProperty("java.class.path"));
        if (this.parentClassLoader instanceof URLClassLoader urlCl) {
            for (URL url : urlCl.getURLs()) {
                String p = url.getFile();
                sb.append(p).append(File.pathSeparator);
            }
        }
        this.classpath = sb.toString();
    }

    public Object javaCodeToObject(String fullClassName, String javaCode) throws IllegalAccessException, InstantiationException {
        // 获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>(); // 错误记录
        // 获取Java文件管理器
        InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(compiler.getStandardFileManager(diagnostics, null, null));

        // 包装输入内容
        List<JavaFileObject> jFiles = List.of(new InMemoryJavaFileManager.InMemorySourceJavaFileObject(fullClassName, javaCode));
        List<String> options = List.of(
            "-encoding", "UTF-8",
            "-classpath", this.classpath);

        // 生成编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jFiles);
        // 执行编译任务
        boolean success = task.call();

        Object instance;
        if (success) {
            InMemoryJavaFileManager.InMemoryOutputJavaFileObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
            Class clazz = dynamicClassLoader.loadClass(fullClassName, jco);
            instance = clazz.newInstance();
        } else {
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                // error += compilePrint(diagnostic);
                error += diagnostic;
            }
            throw new RuntimeException(error);
        }
        return instance;
    }

    private String compilePrint(Diagnostic diagnostic) {
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}
