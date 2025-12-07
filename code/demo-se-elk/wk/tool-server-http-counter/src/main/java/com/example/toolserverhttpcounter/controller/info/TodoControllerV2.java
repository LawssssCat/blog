package com.example.toolserverhttpcounter.controller.info;

import com.example.toolserverhttpcounter.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rest-v2/todo")
public class TodoControllerV2 {
    @RequestMapping(method = GET)
    public Result<String> get() {
        return Result.success("todo");
    }
    @RequestMapping(method = POST)
    public Result<String> post() {
        return Result.success("todo");
    }
}
