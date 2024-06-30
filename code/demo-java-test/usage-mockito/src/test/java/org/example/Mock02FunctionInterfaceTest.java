package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class Mock02FunctionInterfaceTest {
    public static class FuncHandler {
        private String pid;
        private boolean flag = false;

        public FuncHandler(String id) {
            this.pid = "p_" + id;
        }

        public FuncHandler method_01() {
            flag = true;
            return this;
        }

        public <T> T method_02(Function<String, T> func) {
            if (!flag) {
                throw new UnsupportedOperationException("please run method 01 first");
            }
            return func.apply(pid);
        }
    }

    @Test
    void testHandler() {
        assertEquals("xx_p_" + "world", new FuncHandler("world").method_01().method_02(pid -> "xx_" + pid));
        assertThrowsExactly(UnsupportedOperationException.class, () -> new FuncHandler("world").method_02(pid -> "xx_" + pid));
    }

    @Test
    void testHandlerMock() {
        try (MockedConstruction<FuncHandler> funcHandlerMockedConstruction = mockConstruction(FuncHandler.class, new MockedConstruction.MockInitializer<FuncHandler>() {
            final private AtomicInteger i = new AtomicInteger(0);
            private Object answer(InvocationOnMock invocation) throws Throwable {
                if (i.getAndIncrement() == 0) {
                    Function<String, String> arg0 = invocation.getArgument(0);
                    return arg0.apply("p_world");
                } else {
                    return invocation.callRealMethod();
                }
            }

            @Override
            public void prepare(FuncHandler mock, MockedConstruction.Context context) throws Throwable {
                when(mock.method_01()).thenCallRealMethod();
                doAnswer(this::answer).when(mock).method_02(any());
            }
        })) {
            testHandler();
        }
    }
}
