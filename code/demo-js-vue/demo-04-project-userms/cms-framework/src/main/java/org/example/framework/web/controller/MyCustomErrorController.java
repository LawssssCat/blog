package org.example.framework.web.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理404异常
 *
 * <br>
 * 参考：
 * <ul>
 * <li>https://www.cnblogs.com/54chensongxia/p/14007696.html</li>
 * <li>https://www.logicbig.com/tutorials/spring-framework/spring-boot/implementing-error-controller.html</li>
 * </ul>
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class MyCustomErrorController extends BasicErrorController {
    public MyCustomErrorController(ErrorAttributes errorAttributes) {
        // TODO 正确处理
        super(errorAttributes, new ErrorProperties());
    }
}
