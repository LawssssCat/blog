package org.example.entity.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyUrlValidatorTest {
    @Spy
    private MyUrlValidator myUrlValidator = new MyUrlValidator();

    @Test
    void test() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        Assertions.assertTrue(myUrlValidator.isValid("127.0.0.1", context));
        Assertions.assertTrue(myUrlValidator.isValid("example.org", context));
        Assertions.assertTrue(myUrlValidator.isValid("http://example.org:8080/path/to/index.jsp#adfsf?a=1&b=c", context));
        InOrder inOrder = inOrder(myUrlValidator);
        inOrder.verify(myUrlValidator).isIp(anyString());
        inOrder.verify(myUrlValidator).isDomain(anyString());
        inOrder.verify(myUrlValidator).isUrl(anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "localhost",
            "local-host",
            "example.org",
            "www.example.org",
            "www.example.example.org",
    })
    void testDomainTrue(String ip) {
        boolean o = myUrlValidator.isDomain(ip);
        Assertions.assertTrue(o);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "127.0.0.1",
            "www.example.or",
            "www.example.org:8080",
            "http://127.0.0.1",
            "http://255.255.255.255",
            "http://www.example.org",
            "https://www.example.org",
            "https://www.example.org:8080",
            "https://www.example.org:8080/xxx",
            "https://www.example.org:8080/xxx/index.jsp",
            "https://www.example.org:8080/xxx/index.jsp#aaaa",
            "https://www.example.org:8080/xxx/index.jsp#aaaa?aa=bb&cc=dd",
            "-www.example.org",
            "www.example.org-",
            "-www.example.org-",
    })
    void testDomainFalse(String ip) {
        boolean o = myUrlValidator.isDomain(ip);
        Assertions.assertFalse(o);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "127.0.0.1",
            "192.168.1.1",
            "10.0.0.1",
            "255.255.255.255",
    })
    void testIpTrue(String ip) {
        boolean o = myUrlValidator.isIp(ip);
        Assertions.assertTrue(o);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "256.0.0.1",
            "localhost",
            "example.org",
    })
    void testIpFalse(String ip) {
        boolean o = myUrlValidator.isIp(ip);
        Assertions.assertFalse(o);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "localhost",
            "example.org",
            "ftp://www.example.org", // http/https only
    })
    void testUrlFalse(String ip) {
        boolean o = myUrlValidator.isUrl(ip);
        Assertions.assertFalse(o);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://127.0.0.1",
            "http://www.example.org",
            "https://www.example.org",
            "https://www.example.org:8080",
            "https://www.example.org:8080/xxx",
            "https://www.example.org:8080/xxx/index.jsp",
            "https://www.example.org:8080/xxx/index.jsp#aaaa",
            "https://www.example.org:8080/xxx/index.jsp#aaaa?aa=bb&cc=dd",
    })
    void testUrlTrue(String ip) {
        boolean o = myUrlValidator.isUrl(ip);
        Assertions.assertTrue(o);
    }
}
