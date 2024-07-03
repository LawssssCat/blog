package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.config.web.ConcurrentLimit;
import org.example.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/concurrentLimit")
public class ConcurrentLimitController {
    @GetMapping("/sleep/{second}")
    @ConcurrentLimit(max = 3)
    public Result<String> sleep(@PathVariable("second") Integer second) throws InterruptedException {
        TimeUnit.SECONDS.sleep(second);
        return Result.successOf("OK");
    }
}
