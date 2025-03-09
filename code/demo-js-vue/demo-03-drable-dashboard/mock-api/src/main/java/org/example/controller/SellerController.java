package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SellerController {
    @GetMapping("/seller")
    public Object seller() {
        List<Seller> xx = new ArrayList<>();
        xx.add(new Seller("商家1", 101));
        xx.add(new Seller("商家2", 211));
        xx.add(new Seller("商家3", 32));
        xx.add(new Seller("商家4", 11));
        xx.add(new Seller("商家5", 151));
        xx.add(new Seller("商家6", 61));
        xx.add(new Seller("商家7", 198));
        xx.add(new Seller("商家8", 198));
        xx.add(new Seller("商家9", 198));
        xx.add(new Seller("商家10", 1928));
        xx.add(new Seller("商家11", 1928));
        xx.add(new Seller("商家12", 98));
        xx.add(new Seller("商家13", 18));
        xx.add(new Seller("商家14", 18));
        xx.add(new Seller("商家15", 28));
        xx.add(new Seller("商家16", 498));
        return new Result(200, "ok", xx);
    }

    @Data
    @AllArgsConstructor
    static class Seller {
        String name;
        int value;
    }
}
