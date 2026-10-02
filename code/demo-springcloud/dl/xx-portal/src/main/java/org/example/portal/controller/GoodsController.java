package org.example.portal.controller;

import org.example.model.Goods;
import org.example.model.RestResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class GoodsController {
    private static final String GOODS_SERVICE_URL = "http://localhost:9100/service/goods";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/cloud/goods")
    public RestResult<List<Goods>> goods(Model model) {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(GOODS_SERVICE_URL)).build();
        ResponseEntity<RestResult<List<Goods>>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<RestResult<List<Goods>>>() {});
        return responseEntity.getBody();
    }
}
