package org.example.commons.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.model.Goods;
import org.example.commons.model.RestResult;

import java.util.Collections;
import java.util.List;

@Slf4j
public class GoodsRemoteClientFallbackFactory implements FallbackFactory<GoodsRemoteClient> {
    @Override
    public GoodsRemoteClient create(Throwable throwable) {
        return new GoodsRemoteClient() {
            @Override
            public RestResult<List<Goods>> goods() {
                log.info("------ {}", this);
                if (throwable == null) {
                    return RestResult.error("服务降级！！远程服务不可访问！", Collections.emptyList());
                } else {
                    return RestResult.error("服务降级！！内部异常！" + throwable.getMessage(), Collections.emptyList());
                }
            }
        };
    }
}
