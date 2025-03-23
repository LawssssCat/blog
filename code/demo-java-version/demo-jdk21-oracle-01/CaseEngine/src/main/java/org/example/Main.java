package org.example;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Hello world!");
        System.getProperties().forEach((key, value) -> {
            if (contains((String)key, "version", "path")) {
                log.info("{}: {}", key, value);
            }
        });
        log.info("property = {}", System.getProperty("user.dir"));
        log.info("cl = {}", Main.class.getClassLoader());
    }

    private static boolean contains(String str, String... snip) {
        str = str.toLowerCase(Locale.ROOT);
        boolean flag = false;
        for (String s : snip) {
            if (str.contains(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}