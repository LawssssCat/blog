package org.example.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

public class ASMUtils {
    public static void print(String className) throws IOException {
        int parsingOptions = 0 // 会生成所有的ASM代码，包括调试信息、frame信息和代码信息
//                | ClassReader.SKIP_CODE // 会忽略代码信息。例如，会忽略对于MethodVisitor.visitXxxInsn()方法的调用
//                | ClassReader.SKIP_DEBUG // 会忽略调试信息。例如，会忽略对于MethodVisitor.visitParameter()、MethodVisitor.visitLineNumber()、MethodVisitor.visitLocalVariable()等方法的调用
//                | ClassReader.SKIP_FRAMES // 会忽略frame信息。例如，会忽略对于MethodVisitor.visitFrame方法的调用
                | ClassReader.EXPAND_FRAMES // 会对frame信息进行扩展。例如，会对MethodVisitor.visitFrame方法的参数有影响
                ;
        ASMifier printer = new ASMifier();
        PrintWriter printWriter = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        new ClassReader(className).accept(traceClassVisitor, parsingOptions);
    }
}
