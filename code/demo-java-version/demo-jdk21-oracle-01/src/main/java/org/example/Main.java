package org.example;

import java.util.Locale;

import static java.lang.StringTemplate.STR;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.getProperties().forEach((key, value) -> {
            String x = (String) key;
            if (x.toLowerCase(Locale.ROOT).contains("version")) {
                System.out.println(STR."\{key}: \{value}");
            }
        });
    }
}