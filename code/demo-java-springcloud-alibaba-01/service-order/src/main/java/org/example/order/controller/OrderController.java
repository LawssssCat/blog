package org.example.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add() {
        log.info("下单成功！");
        // service-stock -> application server name registed in nacos
        String msg = restTemplate.getForObject("http://service-stock/stock/reduce", String.class);
        return "下单：" + msg;
    }
}
