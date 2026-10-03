package org.example.commons.service;

import lombok.extern.slf4j.Slf4j;
import org.example.commons.model.Goods;
import org.example.commons.model.RestResult;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
public class GoodsRemoteClientFallback implements GoodsRemoteClient {
    @Override
    public RestResult<List<Goods>> goods() {
        log.info("------ {}", this);
        return RestResult.error("服务降级！！远程服务不可访问，或者内部异常！", Collections.emptyList());
    }
}
