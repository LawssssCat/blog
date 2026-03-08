package org.example.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {
    @Value("${server.port}")
    private String port;

    @Resource
    private ServletWebServerApplicationContext webServerAppCtxt;

    private String getPort() {
//        return this.port; // 问题：0时随机选取，但这里仍然是0
        return "" + webServerAppCtxt.getWebServer().getPort();
    }

    @RequestMapping("/reduce")
    public String reduce() {
        log.info("扣减库存");
        return "库存：扣减成功 in " + getPort();
    }
}
