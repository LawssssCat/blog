package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Employee;
import org.example.repository.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class DemoH2SpringbootApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoH2SpringbootApplication.class, args);
    }

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("Inserting -> {}", employeeMapper.insert(new Employee(100L, "Ramesh", "Fadatare", 11, "ramesh@gmail.com")));
        log.info("Inserting -> {}", employeeMapper.insert(new Employee(101L, "John", "Cena", 33, "john@gmail.com")));
        log.info("Inserting -> {}", employeeMapper.insert(new Employee(102L, "tony", "stark", 62, "stark@gmail.com")));
        log.info("Employee id 101 -> {}", employeeMapper.findById(101L));
        log.info("Update 102 -> {}", employeeMapper.update(new Employee(102L, "ram", "Stark", 32,"ramesh123@gmail.com")));
        log.info("Deleting -> {}", employeeMapper.deleteById(101L));
        List<Employee> all = employeeMapper.findAll();
        log.info("All users -> {}", all.size());
        all.forEach(e -> {
            log.info(" - " + e);
        });
    }
}
