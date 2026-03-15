package org.example.lib;

import java.sql.Timestamp;

public class MyPublicFunc {
    private final static String NAME = "hello world! public!";

    public static String name() {
        return NAME + " " + new Timestamp(System.currentTimeMillis());
    }
}
