package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class Init02Test {
    @Mock
    private UserService mockUserService;
    @Spy
    private UserService spyUserService;
    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void test() {
        Assertions.assertTrue(Mockito.mockingDetails(mockUserService).isMock());
        Assertions.assertTrue(Mockito.mockingDetails(spyUserService).isSpy());
    }
}
