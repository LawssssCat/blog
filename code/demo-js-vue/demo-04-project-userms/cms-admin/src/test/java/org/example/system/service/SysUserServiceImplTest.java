package org.example.system.service;

import javax.annotation.Resource;

import org.assertj.core.api.Assertions;
import org.example.common.core.domain.entity.SysUser;
import org.example.system.service.impl.SysUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SysUserServiceImplTest {
    @Resource
    private SysUserServiceImpl sysUserServiceImpl;

    @Test
    void test_selectUserByUserName() {
        SysUser sysUser = sysUserServiceImpl.selectUserByUserName("admin");
        log.info("{}", sysUser);
        Assertions.assertThat(sysUser).isNotNull();
    }
}
