package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnfinishedVerificationException;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerifyTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private MyTester myTester;
    @Test
    void test_verifyNoInteractions() {
        verifyNoInteractions(userService); // 通过，因为无调用 userService
        myTester.doSomething();
        Assertions.assertThrowsExactly(NoInteractionsWanted.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                verifyNoInteractions(userService); // 异常，因为 myTester 内部调用 userService
            }
        });
        verify(userService).getUserAll();
        Assertions.assertThrowsExactly(NoInteractionsWanted.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                verifyNoInteractions(userService); // 异常，因为判断的是 “是否有被调用过”
            }
        });
    }
    @Test
    void test_verifyNoMoreInteractions() {
        verifyNoMoreInteractions(userService); // 通过，因为无调用 userService
        myTester.doSomething();
        Assertions.assertThrowsExactly(NoInteractionsWanted.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                verifyNoMoreInteractions(userService); // 异常，因为 myTester 内部调用 userService
            }
        });
        verify(userService).getUserAll();
        verifyNoMoreInteractions(userService); // 通过，因为判断的是 “是否没有更多异常” 了
    }
    @Test
    void test_validateMockitoUsage() {
        verify(userService);
        Assertions.assertThrowsExactly(UnfinishedVerificationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                validateMockitoUsage(); // 异常，因为 verify 没有方法调用
            }
        });
    }
    private static class MyTester {
        private UserService userService;
        void doSomething() {
            userService.getUserAll();
        }
    }
}
