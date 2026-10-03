package org.example.commons.service;

import org.example.commons.model.RestResult;
import org.example.commons.model.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("xx-goods")
public interface GoodsRemoteClient {
    @RequestMapping("/service/goods")
    RestResult<List<Goods>> goods();
}
