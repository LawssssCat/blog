package org.example.web.controller.test;

import java.util.Collections;

import javax.annotation.Resource;

import org.example.common.core.domain.AjaxResult;
import org.example.common.utils.key.SnowflakeIdUtils;
import org.example.system.mapper.TestMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestMapper testMapper;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public AjaxResult insert() {
        TestMapper.TestEntity record = new TestMapper.TestEntity();
        record.setId(SnowflakeIdUtils.nextTestId());
        testMapper.insert(record);
        return AjaxResult.success("ok");
    }

    @RequestMapping(value = "/insert/batch", method = RequestMethod.POST)
    public AjaxResult insertBatch() {
        TestMapper.TestEntity record = new TestMapper.TestEntity();
        record.setId(SnowflakeIdUtils.nextTestId());
        testMapper.insertList(Collections.singletonList(record));
        return AjaxResult.success("ok");
    }
}
