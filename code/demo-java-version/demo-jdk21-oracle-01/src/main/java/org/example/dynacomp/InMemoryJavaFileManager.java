package org.example.dynacomp;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class InMemoryJavaFileManager extends ForwardingJavaFileManager {
    private InMemoryJavaFileObject jclassObject;

    protected InMemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        jclassObject = new InMemoryJavaFileObject(className, kind);
        return jclassObject;
    }

    public InMemoryJavaFileObject getJavaClassObject() {
        return jclassObject;
    }
}
