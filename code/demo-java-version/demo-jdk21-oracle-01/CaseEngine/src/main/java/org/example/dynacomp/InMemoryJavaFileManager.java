package org.example.dynacomp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.*;

/**
 * @see jdk.jshell.MemoryFileManager
 */
public class InMemoryJavaFileManager extends ForwardingJavaFileManager {
    private InMemoryOutputJavaFileObject jclassObject;

    protected InMemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        jclassObject = new InMemoryOutputJavaFileObject(className, kind);
        // todo 通过接收多次回调，获取并加载子类
        return jclassObject;
    }

    public InMemoryOutputJavaFileObject getJavaClassObject() {
        return jclassObject;
    }

    static class InMemoryJavaFileObject extends SimpleJavaFileObject {
        protected InMemoryJavaFileObject(String name, Kind kind) {
            super(uri(name, kind), kind);
        }

        private static URI uri(String name, Kind kind) {
            String str = "string:///" + name.replace('.', '/') + kind.extension;
            return URI.create(str);
        }
    }

    public static class InMemoryOutputJavaFileObject extends InMemoryJavaFileObject {
        protected ByteArrayOutputStream bos;

        protected InMemoryOutputJavaFileObject(String name, Kind kind) {
            super(name, kind);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            this.bos = new ByteArrayOutputStream();
            return bos;
        }

        public byte[] getBytes() {
            // 编译结果：“.class字节码”
            return bos.toByteArray();
        }
    }

    public static class InMemorySourceJavaFileObject extends InMemoryJavaFileObject {
        private CharSequence content;

        public InMemorySourceJavaFileObject(String className,
            CharSequence content) {
            super(className, Kind.SOURCE);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(
            boolean ignoreEncodingErrors) {
            return content;
        }
    }
}
