package org.example.service;

import org.example.entity.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUserAll();
    List<UserDTO> getUserList(UserDTO param);
}
