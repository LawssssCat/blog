package org.example.dynacomp;

import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassLoader extends URLClassLoader {
    public DynamicClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    protected Class<?> findClassByClassName(String fullName) throws ClassNotFoundException {
        return this.findClass(fullName);
    }

    public Class loadClass(String fullName, byte[] classData) {
        return loadClass(fullName, classData, 0, classData.length);
    }

    public Class loadClass(String fullName, byte[] classData, int off, int len) {
        return this.defineClass(fullName, classData, off, len);
    }
}
