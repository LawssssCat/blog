package org.example.system.mapper;

import javax.persistence.Table;

import org.example.common.core.domain.BaseEntity;

import lombok.Data;
import tk.mybatis.mapper.common.Mapper;

public interface TestMapper extends Mapper<TestMapper.TestEntity> {
    @Table(name = "T_TEST")
    @Data
    class TestEntity extends BaseEntity {

    }
}
