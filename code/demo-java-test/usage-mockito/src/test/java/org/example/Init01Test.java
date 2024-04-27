package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Init01Test {
    @Mock // @InjectMocks —— 前者不调用实际方法，后者调用实际方法
    private UserService mockUserService;
    @Spy
    private UserService spyUserService;
    @Test
    void test() {
        Assertions.assertTrue(Mockito.mockingDetails(mockUserService).isMock());
        Assertions.assertFalse(Mockito.mockingDetails(mockUserService).isSpy()); // 💡 mock is not spy —— mock 实际为空调用，而 spy 需要监听实际方法调用，因此冲突
        Assertions.assertTrue(Mockito.mockingDetails(spyUserService).isSpy());
        Assertions.assertTrue(Mockito.mockingDetails(spyUserService).isMock()); // 💡 spy is mock —— 在 spy 监听实际方法调用的同时，提供 mock 改变返回值的功能！
    }
}
