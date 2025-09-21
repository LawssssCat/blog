package org.example.web.controller.test;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.example.common.core.domain.AjaxResult;
import org.example.common.utils.key.SnowflakeIdUtils;
import org.example.system.mapper.TestMapper;
import org.example.system.mapper.TestMapper.TestEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestMapper testMapper;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public AjaxResult insert(@RequestBody TestEntity record) {
        record.setId(SnowflakeIdUtils.nextTestId());
        testMapper.insert(record);
        return AjaxResult.success("ok");
    }

    @RequestMapping(value = "/insert/batch", method = RequestMethod.POST)
    public AjaxResult insertBatch() {
        TestEntity record = new TestEntity();
        record.setId(SnowflakeIdUtils.nextTestId());
        testMapper.insertList(Collections.singletonList(record));
        return AjaxResult.success("ok");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public AjaxResult search(@RequestParam("s") String str) {
        str = str; // escape _ -> \_
        Example example = new Example(TestEntity.class);
        example.createCriteria().andLike(TestEntity.Fields.name, "%" + str + "%");
        List<TestEntity> testEntities = testMapper.selectByExample(example);
        return AjaxResult.successData(testEntities);
    }
}
