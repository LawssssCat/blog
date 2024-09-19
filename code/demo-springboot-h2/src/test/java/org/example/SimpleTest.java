package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Employee;
import org.example.repository.EmployeeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class SimpleTest {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Transactional
    @Rollback
    @Order(0)
    @Test
    void test0() {
        assertEquals(1, employeeMapper.insert(new Employee(100L, "Ramesh", "Fadatare", 11, "ramesh@gmail.com")));
        assertEquals(1, employeeMapper.insert(new Employee(101L, "John", "Cena", 33, "john@gmail.com")));
        assertEquals(1, employeeMapper.insert(new Employee(102L, "tony", "stark", 62, "stark@gmail.com")));
        assertNotNull(employeeMapper.findById(101L));
        assertEquals(1, employeeMapper.update(new Employee(102L, "ram", "Stark", 32,"ramesh123@gmail.com")));
        assertEquals(1, employeeMapper.deleteById(101L));
        employeeMapper.findAll().forEach(e -> {
            log.info("all-" + e);
        });
    }

    @Order(1)
    @Test
    void test1() {
        // testRollback
        assertNull(employeeMapper.findById(101L));
        employeeMapper.findAll().forEach(e -> {
            log.info("non-" + e);
        });
    }
}
