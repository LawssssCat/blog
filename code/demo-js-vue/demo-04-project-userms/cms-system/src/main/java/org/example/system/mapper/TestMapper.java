package org.example.system.mapper;

import javax.persistence.Column;
import javax.persistence.Table;

import org.example.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.update.batch.BatchUpdateMapper;
import tk.mybatis.mapper.common.Mapper;

public interface TestMapper extends Mapper<TestMapper.TestEntity>,
    InsertListMapper<TestMapper.TestEntity>,
    BatchUpdateMapper<TestMapper.TestEntity> {
    @Table(name = "T_TEST")
    @Data
    @FieldNameConstants
    class TestEntity extends BaseEntity {
        @Column(name = "name")
        private String name;
    }
}
