package org.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证权限配置
 *
 * @see <a href="https://www.cnblogs.com/qiantao/p/14605154.html">Spring整合SpringSecurity笔记</a>
 */
@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String request() {
        log.debug("controller: /hello!");
        return "hello";
    }
}
