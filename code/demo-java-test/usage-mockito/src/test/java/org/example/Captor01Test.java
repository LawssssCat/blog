package org.example;

import org.example.entity.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

public @ExtendWith(MockitoExtension.class)
class Captor01Test {
    @Mock
    private UserService userService;
    @Captor
    ArgumentCaptor<UserDTO> argCaptor;
    @Test
    void test_when_capture() {
        when(userService.getUserList(argCaptor.capture())).thenReturn(null);
        UserDTO userDTO = new UserDTO();
        Assertions.assertNull(userService.getUserList(userDTO));
        Assertions.assertEquals(userDTO, argCaptor.getValue());
    }
    @Test
    void test_verify_capture() {
        UserDTO userDTO = new UserDTO();
        userService.getUserList(userDTO);
        verify(userService, times(1)).getUserList(argCaptor.capture());
        Assertions.assertEquals(userDTO, argCaptor.getValue());
    }
}
