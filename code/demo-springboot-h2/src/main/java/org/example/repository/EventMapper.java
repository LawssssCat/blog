package org.example.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.Event;
import org.example.model.EventPoParam;

import java.util.List;

@Mapper
public interface EventMapper {
    List<Event> findByParam(@Param("param")EventPoParam param);
}
