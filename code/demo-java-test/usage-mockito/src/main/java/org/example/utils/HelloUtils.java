package org.example.utils;

import org.example.entity.UserDTO;

public class HelloUtils {
    public static String getMsg(String msg) {
        return msg;
    }
    public static UserDTO toUser(String name, int age) {
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setAge(age);
        return user;
    }
}
