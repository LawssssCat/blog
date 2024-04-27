package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * 区分 @Mock、@Spy、@Captor、@InjectMock
 */
@ExtendWith(MockitoExtension.class)
public class DiffTypesTest {
    @Mock
    private List<String> mock;
    @Spy
    private List<String> spyNoInstance; // 💡如果没有实例化，则退化成 mock （即不调用实际方法）
    @Spy
    private List<String> spy = new ArrayList<>();
    @Captor
    private ArgumentCaptor<String> captor; // 用于获取入参
    /**
     * 自动注入相关引用
     */
    @InjectMocks
    private InjectMockClass injectMockClass = new InjectMockClass();
    /**
     * \@Mock 不实际调用方法
     */
    @Test
    void test_mock_do_nothing() {
        mock.add("1");
        Assertions.assertEquals(0, mock.size());
    }
    /**
     * \@Spy 实际调用方法
     */
    @Test
    void test_spy_do_exactly() {
        spy.add("1");
        Assertions.assertEquals(1, spy.size());
        spyNoInstance.add("1");
        Assertions.assertEquals(0, spyNoInstance.size());
    }
    @Test
    void test_captor() {
        spy.add("1");
        verify(spy).add(captor.capture());
        Assertions.assertEquals(1, spy.size());
        Assertions.assertEquals("1", captor.getValue());
    }
    /**
     * \@InjectMock 注入相关 @Mock/@Spy 进入对象属性中
     */
    @Test
    void test_injectMock() {
        // 自动注入相关属性
        Assertions.assertEquals(mock, injectMockClass.mock);
        Assertions.assertEquals(spy, injectMockClass.spy);
        Assertions.assertFalse(mockingDetails(injectMockClass).isMock()); // 被注入属性的对象默认不是 Mock
        Assertions.assertEquals(1, injectMockClass.doSomething()); // 实际执行方法
    }
    private static class InjectMockClass {
        private List<String> mock;
        private List<String> spy;
        int doSomething() {
            return 1;
        }
    }
}
