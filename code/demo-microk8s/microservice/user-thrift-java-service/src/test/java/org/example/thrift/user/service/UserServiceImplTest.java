package org.example.thrift.user.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.example.thrift.user.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Test
    void test_getUserById() throws TException {
        UserInfo userInfo = userServiceImpl.getUserById(1);
        log.info("User -> {}", userInfo);
        Assertions.assertNotNull(userInfo);
    }
}
