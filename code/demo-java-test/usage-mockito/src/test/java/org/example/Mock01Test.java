package org.example;

import org.example.entity.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnnecessaryStubbingException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 模拟返回结果/抛出异常
 */
@ExtendWith(MockitoExtension.class)
public class Mock01Test {
    @Mock
    private UserService mockUserService;
    /**
     * 测试 when 的 thenReturn 方法
     * 💡调用多个 thenReturn 时，会按顺序返回
     */
    @Test
    void test_when_thenReturn() {
        List<UserDTO> returnUserAll01 = List.of(new UserDTO());
        List<UserDTO> returnUserAll02 = List.of(new UserDTO());
        when(mockUserService.getUserAll()).thenReturn(returnUserAll01).thenReturn(returnUserAll02); // 顺序返回
        Assertions.assertArrayEquals(returnUserAll01.toArray(), mockUserService.getUserAll().toArray());
        Assertions.assertArrayEquals(returnUserAll02.toArray(), mockUserService.getUserAll().toArray());
        Assertions.assertArrayEquals(returnUserAll02.toArray(), mockUserService.getUserAll().toArray()); // 返回最后一个 thenReturn 设定
    }

    /**
     * 另一种写法
     */
    @Test
    void test_when_thenReturn_2() {
        List<UserDTO> returnUserAll01 = List.of(new UserDTO());
        doReturn(returnUserAll01).when(mockUserService).getUserAll();
        Assertions.assertArrayEquals(returnUserAll01.toArray(), mockUserService.getUserAll().toArray());
    }
    /**
     * ⚠️定义了 when 就要调用，否则抛异常 {@link UnnecessaryStubbingException}
     * ⚠️如果确定方法 mock 后不调用，一种处理方法是使用 lenient() 提供的方法进行 mock
     * 💡另一种方法是在 mock 环境建立前设置宽松的 mock 行为： Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
     */
    @Test
    void test_UnnecessaryStubbingException_lenient() {
        // ↓ or @Mock(lenient = true)
        lenient().when(mockUserService.getUserAll()).thenReturn(new ArrayList<>()); // this won't get called
    }
    /**
     * 动态返回
     */
    @Test
    void test_thenAnswer() {
        UserDTO param = new UserDTO();
        when(mockUserService.getUserList(any(UserDTO.class))).thenAnswer((Answer<List<UserDTO>>) invocation -> {
            UserDTO argument = invocation.getArgument(0);
            return List.of(argument); // 💡运行时获取传入参数（而不是 when 时就指定）
        });
        Assertions.assertArrayEquals(List.of(param).toArray(), mockUserService.getUserList(param).toArray());
    }
    /**
     * 模拟异常抛出
     */
    @Test
    void test_doThrow() {
        doThrow(ArithmeticException.class).when(mockUserService).getUserAll();
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockUserService.getUserAll();
            }
        });
    }
}
