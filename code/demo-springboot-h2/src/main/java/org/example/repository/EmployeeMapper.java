package org.example.repository;

import org.apache.ibatis.annotations.*;
import org.example.model.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employees")
    List<Employee> findAll();

    @Select("SELECT * FROM employees WHERE id = #{id}")
    Employee findById(long id);

    @Delete("DELETE FROM employees WHERE id = #{id}")
    int deleteById(long id);

    @Insert("INSERT INTO employees(id, first_name, last_name, age, email_address) " +
            " VALUES (#{id}, #{firstName}, #{lastName}, #{age}, #{emailId})")
    int insert(Employee employee);

    @Update("Update employees set first_name=#{firstName}, " +
            " last_name=#{lastName}, age=#{age}, email_address=#{emailId} where id=#{id}")
    int update(Employee employee);
}
