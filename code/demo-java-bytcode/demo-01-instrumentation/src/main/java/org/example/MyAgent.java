package org.example;

import org.example.other.MyClass;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {
    // 这个方法在premain启动时调用
    public static void premain(String args, Instrumentation inst) {
        main(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
    }

    private static void main(String args, Instrumentation inst) {
        System.out.println("=========== premain ===========");
        // 添加字节码转换器
        inst.addTransformer(new MyTransformer());

        // 重新定义已加载的类（如果需要）
        try {
            Class<?> clazz = MyClass.class;
            byte[] newByteCode = modifyClassByteCode(clazz);
            ClassDefinition classDefinition = new ClassDefinition(clazz, newByteCode);
            inst.redefineClasses(classDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] modifyClassByteCode(Class<?> clazz) {
        // 在此实现字节码的修改逻辑
        return null; // 返回修改后的字节码
    }

    private static class MyTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            // 这里可以修改字节码，当前演示只是打印类名
            System.out.println("className = " + className);
            // 不修改字节码，字节返回
            return classfileBuffer;
        }
    }
}
