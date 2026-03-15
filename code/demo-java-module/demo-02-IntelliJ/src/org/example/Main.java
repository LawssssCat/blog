package org.example;

import org.example.lib.MyPublicFunc;
// import org.example.lib.inner.MyInnerFunc;

import javax.xml.XMLConstants;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * main
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("args = " + Arrays.toString(args));
        // module jdk xml
        System.out.println("XMLConstants.XML_NS_URI = " + XMLConstants.XML_NS_URI);
        // module custom
        System.out.println("MyPublicFunc.name() = " + MyPublicFunc.name());
        try {
            // Unable to make field private static final java.lang.String org.example.lib.MyPublicFunc.NAME accessible:
            // module demo.lib does not "opens org.example.lib" to module demo.main
            Field field = MyPublicFunc.class.getDeclaredField("NAME");
            field.setAccessible(true);
            System.out.println("MyPublicFunc.class.getDeclaredField(\"NAME\").get(MyPublicFunc.class) = " + field.get(MyPublicFunc.class));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // System.out.println("MyInnerFunc.name() = " + MyInnerFunc.name()); // 强封装：模块未导出的包无法被使用
        System.out.println("ok");
    }
}
