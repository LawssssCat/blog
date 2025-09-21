package org.example.web.controller.hello;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Maps;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = {HelloController.class})
public class HelloControllerAdvice {
    @Resource
    private HttpServletRequest request;

    @ModelAttribute
    public void initDate1(Model model) {
        log.debug("initData1");
        request.setAttribute("num1", 66);
        model.addAttribute("userMail", "123@example.org");
    }

    @ModelAttribute(name = "userInfo")
    public Map<String, String> initData2() {
        log.debug("initData2");
        request.setAttribute("num2", 88);
        return Maps.newHashMap("name", "kit"); // 存入 Model 中，作为 userInfo 的值
    }
}
