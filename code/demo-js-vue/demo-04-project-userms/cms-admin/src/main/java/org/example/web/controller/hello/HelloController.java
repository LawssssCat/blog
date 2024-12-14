package org.example.web.controller.hello;

import java.util.Map;

import org.example.common.core.domain.AjaxResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证权限配置
 *
 * @see <a href="https://www.cnblogs.com/qiantao/p/14605154.html">Spring整合SpringSecurity笔记</a>
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/hi")
    public AjaxResult hi(Model model) {
        log.info("model: {}", model);
        return AjaxResult.successData("hello");
    }

    @PostMapping("/hi")
    public AjaxResult hiPost(@RequestParam("param1") String param1,
        @RequestParam("param2") String param2) {
        log.info("p1:{}, p2:{}", param1, param2);
        return AjaxResult.successData("hello post");
    }

    @PutMapping("/hi")
    public AjaxResult hiPut(@RequestBody Map<String, Object> map) {
        log.info("map: {}", map);
        return AjaxResult.successData("hello put");
    }
}
