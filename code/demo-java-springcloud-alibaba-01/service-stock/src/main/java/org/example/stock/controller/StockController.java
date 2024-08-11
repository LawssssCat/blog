package org.example.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {
    @RequestMapping("/reduce")
    public String reduce() {
        log.info("扣减库存");
        return "库存：扣减成功";
    }
}
