package org.example.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.commons.model.RestResult;
import org.example.commons.service.GoodsRemoteClient;
import org.example.commons.model.Goods;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class GoodsController {
    @Resource
    private GoodsRemoteClient goodsRemoteClient;

    @RequestMapping("/cloud/goods")
    public RestResult<List<Goods>> goods(Model model) {
        return goodsRemoteClient.goods();
    }
}
