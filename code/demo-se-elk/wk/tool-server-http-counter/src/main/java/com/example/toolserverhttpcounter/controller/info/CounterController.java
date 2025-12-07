package com.example.toolserverhttpcounter.controller.info;

import com.example.toolserverhttpcounter.bean.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/counter")
public class CounterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterController.class);

    private static final long PRINT_RATE_VALUE = 1;

    private final Object LOCK = new Object();

    private final SortedMap<String, Long> store = new TreeMap<>(Comparators.comparable());

    ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = PRINT_RATE_VALUE, timeUnit = TimeUnit.SECONDS)
    public void printer() {
        LOGGER.info("counter, rate={}s", PRINT_RATE_VALUE);
        synchronized (LOCK) {
            for (Map.Entry<String, Long> entry : store.entrySet()) {
                LOGGER.info("| {} = {}", entry.getKey(), entry.getValue());
            }
            store.clear();
        }
    }

    private void count(String path, String msg) {
        synchronized (LOCK) {
            Long value = store.getOrDefault(path, 0L) + 1L;
            store.put(path, value);
            x: if (StringUtils.hasText(msg)) {
                String keyBody = path + "&body";
                int bodySize = msg.length();
                long allBodySize = store.getOrDefault(keyBody, 0L) + bodySize;
                store.put(keyBody, allBodySize);
                List<Object> list;
                try {
                    list = objectMapper.readValue(msg, objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
                } catch (JsonProcessingException e) {
                    LOGGER.error("xxxxx", e);
                    break x;
                }
                int arraySize = list.size();
                String keyArray = path + "&array_size";
                long allArraySize = store.getOrDefault(keyArray, 0L) + arraySize;
                store.put(keyArray, allArraySize);
                store.put(keyArray + "pB", allBodySize / allArraySize);
                store.put(keyArray + "pKB", allBodySize / allArraySize / 1024);
                store.put(keyArray + "pMB", allBodySize / allArraySize / 1024 / 1024);
            }
        }
    }

    @RequestMapping(method = GET, path = "/**")
    public Result<String> xget(HttpServletRequest request) {
        count(request.getRequestURI(), null);
        return Result.success("ok");
    }
    @RequestMapping(method = POST, path = "/**")
    public Result<String> x(HttpServletRequest request, @RequestBody String body) {
        count(request.getRequestURI(), body);
        return Result.success("ok");
    }
}
