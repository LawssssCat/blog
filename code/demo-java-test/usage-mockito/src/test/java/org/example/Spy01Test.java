package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class Spy01Test {
    @Spy
    private UserService userService;
    @Test
    void test_spy_is_mock() {
        Assertions.assertTrue(mockingDetails(userService).isMock()); // 既是 spy 也是 mock
        Assertions.assertTrue(mockingDetails(userService).isSpy());
    }

    /**
     * 测试 verify 抛出异常的情况
     */
    @Test
    void test_verify() {
        Assertions.assertThrows(WantedButNotInvoked.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                verify(userService).getUserAll(); // 校验发现方法未被调用（至少一次），则抛出异常
                // verify(userService, times(1)).getUserAll(); // 等价 ↑
            }
        });
        userService.getUserAll();
        verify(userService).getUserAll(); // 调用后再校验，则不报错
    }

    /**
     * 测试 spy 的实例与 spy 是否关联
     */
    @Test
    void test_instance_change() {
        List<String> list = new ArrayList<>();
        List<String> spy = Mockito.spy(list);
        spy.add("1");
        Assertions.assertEquals(1, spy.size());
        Assertions.assertEquals(0, list.size()); // spy 与实例并不关联，相互独立
    }
}
