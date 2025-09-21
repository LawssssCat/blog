package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Event;
import org.example.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@SpringBootTest
public class TransactionTest {
    @Resource
    private EventService eventService;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Transactional
    @Test
    void testInsert() {
        // Creating new transaction with name [org.example.TransactionTest.testInsert]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        // HikariProxyConnection@41029700
        log.warn("---");
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        Integer count = transactionTemplate.execute((status) -> {
            // Suspending current transaction, creating new transaction with name [null]
            // HikariProxyConnection@1223357487
            log.warn("---");
            Event event = new Event(99, "{\"createdAt\":\"" + new Date().getTime() + "\"}");
            int c = eventService.createEvent(event);
            log.warn("---");
            return c;
            // Committing JDBC transaction on Connection [HikariProxyConnection@1223357487 wrapping conn1: url=jdbc:h2:mem:test user=SA]
            // Releasing JDBC Connection [HikariProxyConnection@1223357487 wrapping conn1: url=jdbc:h2:mem:test user=SA] after transaction
            // Resuming suspended transaction after completion of inner transaction
        });
        log.warn("---");
        Assertions.assertEquals(1, count);
        // Rolling back JDBC transaction on Connection [HikariProxyConnection@41029700 wrapping conn0: url=jdbc:h2:mem:test user=SA]
    }

    @Transactional
    @Test
    void testUpdateById() {

    }

    private <T> T xx(TransactionCallback<T> action) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(action);
    }
}
