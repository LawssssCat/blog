package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.model.po.EventPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EventMapper {
    @Options(useGeneratedKeys = true, keyProperty = EventPo.Fields.id)
    @Insert("insert into event (status, name, param, context) values (#{event.status}, #{event.name}, #{event.param} format json, #{event.context} format json)")
    int insert(@Param("event") EventPo event);

    List<EventPo> selectByParam(@Param("event") EventPo event);

    List<EventPo> selectByParamForUpdate(@Param("event") EventPo eventPo);

    int updateById(@Param("event") EventPo eventPo);
}
