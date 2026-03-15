package org.example;

import org.example.lib.MyPublicFunc;
// import org.example.lib.inner.MyInnerFunc;

import javax.xml.XMLConstants;
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
        // System.out.println("MyInnerFunc.name() = " + MyInnerFunc.name()); // 强封装：模块未导出的包无法被使用
    }
}
