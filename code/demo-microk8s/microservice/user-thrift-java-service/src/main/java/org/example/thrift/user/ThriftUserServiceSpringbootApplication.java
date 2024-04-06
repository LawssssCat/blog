package org.example.thrift.user;

import org.example.thrift.user.thrift.ThriftServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThriftUserServiceSpringbootApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ThriftUserServiceSpringbootApplication.class, args);
    }

    @Autowired
    private ThriftServer thriftServer;

    @Override
    public void run(String... args) throws Exception {
        thriftServer.startThriftServer();
    }
}
