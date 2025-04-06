package com.example.democli.demos.mapper;

import com.example.democli.demos.web.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {
    List<User> selectAll();
}
