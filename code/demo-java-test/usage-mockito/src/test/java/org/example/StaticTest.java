package org.example;

import org.example.entity.UserDTO;
import org.example.utils.HelloUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaticTest {
    @Test
    void testMockStatic() {
        try (MockedStatic<HelloUtils> helloUtilsMockedStatic = mockStatic(HelloUtils.class)) {
            helloUtilsMockedStatic.when(() -> HelloUtils.getMsg(anyString())).thenReturn("hello");
            Assertions.assertEquals("hello", HelloUtils.getMsg("1"));
            Assertions.assertEquals("hello", HelloUtils.getMsg("2"));
        }
    }

    @Test
    void test() {
        try (MockedConstruction<UserDTO> userDTOMockedConstruction = mockConstruction(UserDTO.class, new MockedConstruction.MockInitializer<UserDTO>() {
            @Override
            public void prepare(UserDTO mock, MockedConstruction.Context context) throws Throwable {
                doAnswer(invocation -> {
                    Field field = UserDTO.class.getDeclaredField("name");
                    field.setAccessible(true);
                    field.set(mock, "mockName");
                    return null;
                }).when(mock).setName(anyString());
                doAnswer(invocation -> {
                    Field field = UserDTO.class.getDeclaredField("age");
                    field.setAccessible(true);
                    field.set(mock, 9999);
                    return null;
                }).when(mock).setAge(anyInt());
                when(mock.getAge()).thenCallRealMethod();
                when(mock.getName()).thenCallRealMethod();
            }
        })) {
            UserDTO user = HelloUtils.toUser("username", 18);
            Assertions.assertEquals("mockName", user.getName());
            Assertions.assertEquals(9999, user.getAge());
        }
    }
}
