package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class Init03Test {
    private UserService mockUserService;
    private UserService spyUserService;
    @BeforeEach
    public void beforeEach() {
        mockUserService = Mockito.mock(UserService.class);
        spyUserService = Mockito.spy(UserService.class);
    }
    @Test
    void test() {
        Assertions.assertTrue(Mockito.mockingDetails(mockUserService).isMock());
        Assertions.assertTrue(Mockito.mockingDetails(spyUserService).isSpy());
    }
}
