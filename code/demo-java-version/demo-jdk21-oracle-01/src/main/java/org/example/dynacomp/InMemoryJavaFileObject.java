package org.example.dynacomp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    protected InMemoryJavaFileObject(String name, Kind kind) {
        super(uri(name, kind), kind);
    }

    private static URI uri(String name, Kind kind) {
        String str = "string:///" + name.replace('.', '/') + kind.extension;
        return URI.create(str);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }
}
