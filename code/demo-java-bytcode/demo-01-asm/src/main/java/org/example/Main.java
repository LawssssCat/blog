package org.example;

import org.example.utils.ASMUtils;
import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Main {
    public static final URL RESOURCE = Main.class.getResource("/");

    public static void main(String[] args) throws Exception {
        System.out.println("============ 1 生成 ==============");
        // 生成class二进制
        final byte[] bytes = generate("org/example/HelloWorld");
        wirteBytes("org/example/HelloWorld.class", bytes);
        // 调用方法
        Class<?> clazz = defineClass("org.example.HelloWorld");
        invokeInstanceMethod(clazz, "hello");
        // 打印类ASM信息
        ASMUtils.print("org.example.HelloWorld");

        System.out.println("============ 2 改造 ==============");
        final byte[] bytes2 = update(bytes);
        wirteBytes("org/example/HelloWorld2.class", bytes2);
    }

    private static byte[] update(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(0);
        System.out.println("classReader.getClassName() = " + classReader.getClassName());
        int asm9 = Opcodes.ASM9;
        classReader.accept(new ClassVisitor(asm9, classWriter) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.out.printf("Now visit class start, classFileVersion:%s, access:%s, className:%s, signature:%s, superName:%s, interfaces:%s%n",
                        version, access, name, signature, superName, Arrays.toString(interfaces));
                super.visit(version, access, name, signature, superName, interfaces);
            }
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.printf("Now visit field, access:%s, name:%s, descriptor:%s, signature:%s, value:%s%n",
                        access, name, descriptor, signature, value);
                FieldVisitor originalFieldVisitor = super.visitField(access, name, descriptor, signature, value);
                // return null;
                return new FieldVisitor(asm9, originalFieldVisitor) {
                    @Override
                    public void visitAttribute(Attribute attribute) {
                        System.out.printf("- Attribeute: %s%n", attribute);
                        super.visitAttribute(attribute);
                    }
                    @Override
                    public void visitEnd() {
                        System.out.printf("- Now visit field finish. %s%n", name);
                        super.visitEnd();
                    }
                };
            }
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.printf("Now visit field, access:%s, name:%s, descriptor:%s, signature:%s, exceptions:%s%n",
                        access, name, descriptor, signature, Arrays.toString(exceptions));
                MethodVisitor originalMethodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                // return null;
                return new MethodVisitor(asm9, originalMethodVisitor) {
                    @Override
                    public void visitParameter(String pname, int access) {
                        System.out.printf("- Method:%s, Parameter:%s, Access:%s%n", name, pname, access);
                        super.visitParameter(pname, access);
                    }
                    @Override
                    public void visitCode() {
                        System.out.printf("- Method:%s, code start%n", name);
                        super.visitCode();
                    }
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String mname, String descriptor, boolean isInterface) {
                        System.out.printf("- Method:%s, opcode:%s, invoke owner method %s %s, descriptor:%s, isInterface:%s%n", name, opcode, owner, mname, descriptor, isInterface);
                        super.visitMethodInsn(opcode, owner, mname, descriptor, isInterface);
                    }
                    @Override
                    public void visitLineNumber(int line, Label start) {
                        System.out.printf("- Method:%s, current line number %s, Label:%s%n", name, line, start);
                        super.visitLineNumber(line, start);
                    }
                    @Override
                    public void visitMaxs(int maxStack, int maxLocals) {
                        System.out.printf("- Method:%s, maxStack:%s, maxLocals:%s%n", name, maxStack, maxLocals);
                        super.visitMaxs(maxStack, maxLocals);
                    }
                    @Override
                    public void visitEnd() {
                        System.out.printf("- Method:%s, visit finish%n", name);
                        super.visitEnd();
                    }
                };
            }
            @Override
            public void visitEnd() {
                System.out.println("Now visit class finished");
                MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "oooUpdate", "()V", null, null);
                methodVisitor.visitCode();
                methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream");
                methodVisitor.visitLdcInsn("oooUpdate_parameter");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                methodVisitor.visitInsn(Opcodes.RETURN);
                methodVisitor.visitMaxs(2, 1);
                methodVisitor.visitEnd();
                super.visitEnd();
            }
        }, 0 | ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private static void invokeInstanceMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = clazz.getConstructor();
        Object o = constructor.newInstance();
        Method method = clazz.getMethod(methodName);
        method.invoke(o);
    }

    private static Class<?> defineClass(String className) throws ClassNotFoundException {
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {RESOURCE});
        Class<?> aClass = urlClassLoader.loadClass(className);
        return aClass;
    }

    private static void wirteBytes(String filePath, byte[] bytes) throws IOException {
        File file = new File(RESOURCE.getFile(), filePath);
        file.getParentFile().mkdirs();
        Files.write(file.toPath(), bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    /**
     * 可以通过Idea插件生成如下ASM代码。
     * IDEA插件：
     * - ASM Bytecode Outline
     */
    private static byte[] generate(String className) {
        // 1 准备写二进制流对象
        int computeFrames = 0; // ASM不会自动计算max stacks、max locals、stack map frames
        computeFrames = ClassWriter.COMPUTE_MAXS; // ASM会自动计算max stacks、max locals，但不会自动计算stack map frames
        computeFrames = ClassWriter.COMPUTE_FRAMES; // ASM会自动计算max stacks、max locals、stack map frames
        // Stack Map Frames = 当前栈的状态以及局部变量表的状态
        // Max Stacks = 操作数栈大小
        // Max Locals = 最大局部变量大小
        ClassWriter cw = new ClassWriter(computeFrames);

        // 2 定义类
        int javaVersion = Opcodes.V16;
        int classAccess = Opcodes.ACC_PUBLIC;
        String classSuperName = "java/lang/Object";
        String classSignature = null; // 泛型
        String[] classInterfaceName = {};
        cw.visit(javaVersion, classAccess, className, classSignature, classSuperName, classInterfaceName);
        {
            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PRIVATE, "name", "Ljava/lang/String;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            // 构造函数
            /*
             public HelloWorld() {}
             */
            // Java 类型ClassFile 描述符
            // boolean Z（Z 表示 Zero，零表示 false，非零表示 true）
            // byte B
            // char C
            // double D
            // float F
            // int I
            // long J
            // short S
            // void V
            // non-array referenceL;
            // array reference [
            String methodReturn = "()V"; // ()=没有参数，V=返回值void类型
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", methodReturn, null, null);
            // (visitParameter)*
            // [visitAnnotationDefault]
            // (visitAnnotation | visitAnnotableParameterCount | visitParameterAnnotation | visitTypeAnnotation)
            // [visitCode ( ———————— ⭐调用一次
            methodVisitor.visitCode(); // 方法内部定义，开始
                // visitFrame
                // | visitXxxInsn ———————— ⭐可以调用多次，用于构建方法体
                // | visitLabel
                // | visitInsnAnnotation
                // | visitTryCatchBlock
                // | visitTryCatchAnnotation
                // | visitLocalVariable
                // | visitLocalVariableAnnotation
                // | visitLineNumber
                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0); // todo ??
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false); // 调用父类（Object）构造方法
                methodVisitor.visitInsn(Opcodes.RETURN);
                // )*
                // visitMaxs ———————— ⭐调用一次
                methodVisitor.visitMaxs(1, 1); // todo ??
            // ]
            // visitEnd ———————— ⭐调用一次
            methodVisitor.visitEnd();
        }
        {
            // 实例函数
            /*
            public void hello() {
                public void hello() {
                  System.out.println("Hello ASM");
                }
            }
             */
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "hello", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("Hello ASM");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }
}
