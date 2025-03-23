package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Hello world!");
        System.getProperties().forEach((key, value) -> {
            String x = (String) key;
            if (x.toLowerCase(Locale.ROOT).contains("version")) {
                log.info("{}: {}", key, value);
            }
        });
        log.info("property = " + System.getProperty("user.dir"));
    }
}