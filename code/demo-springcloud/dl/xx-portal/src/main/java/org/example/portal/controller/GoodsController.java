package org.example.portal.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.example.commons.model.RestResult;
import org.example.commons.service.GoodsRemoteClient;
import org.example.commons.model.Goods;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class GoodsController {
    @Resource
    private GoodsRemoteClient goodsRemoteClient;

    @RequestMapping("/cloud/goods")
    public RestResult<List<Goods>> goods(Model model) {
        return goodsRemoteClient.goods();
    }

    @HystrixCommand(
            // 降级：默认1s超时后降级
            fallbackMethod = "timeout_fallback",
            // 限流：并发超过coreSize+maxQueueSize就会出现限流，呈现降级效果
            threadPoolKey = "test",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),
                    @HystrixProperty(name = "maxQueueSize", value = "1"),
            }
    )
    @RequestMapping("/cloud/timeout")
    public RestResult<String> timeout(Model model, @RequestParam(value = "t", required = false) Float t) {
        if (t == null || t < 0) {
            t = 1F;
        }
        long duration = (long) (t * 1000);
        log.info("------------ duration = {}", duration);
        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return RestResult.ok("ok");
    }

    // 坑：回调参数要与原方法一致，最多加异常参数
    public RestResult<String> timeout_fallback(Model model, Float t, Throwable e) {
        return RestResult.error("服务降级！！！！！！！！！！");
    }
}
