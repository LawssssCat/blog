package org.example.goods.controller;

import org.example.goods.service.GoodsService;
import org.example.commons.model.RestResult;
import org.example.commons.model.Goods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @RequestMapping("/service/goods")
    public RestResult<List<Goods>> goods(Model model) {
        List<Goods> allGoods = goodsService.getAllGoods();
        return RestResult.ok(allGoods);
    }

    @Value("${my.boom.info}")
    private String boomInfo;

    @RequestMapping("/service/boom")
    public RestResult<String> boom() {
        return RestResult.ok("炸裂信息：" + boomInfo);
    }
}
