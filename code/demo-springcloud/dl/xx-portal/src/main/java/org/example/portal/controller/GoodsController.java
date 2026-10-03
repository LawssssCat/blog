package org.example.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.RestApi;
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
@Slf4j
public class GoodsController {
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/cloud/goods")
    public RestResult<List<Goods>> goods(Model model) {
        URI url = URI.create(RestApi.Goods.GET_SERVICE_GOODS);
        log.info("------ {}", url);
        RequestEntity<Void> requestEntity = RequestEntity.get(url).build();
        ResponseEntity<RestResult<List<Goods>>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<RestResult<List<Goods>>>() {});
        log.info("======== {}", responseEntity.getStatusCode());
        log.info("======== {}", responseEntity.getHeaders());
        log.info("======== {}", responseEntity.getBody());
        return responseEntity.getBody();
    }
}
