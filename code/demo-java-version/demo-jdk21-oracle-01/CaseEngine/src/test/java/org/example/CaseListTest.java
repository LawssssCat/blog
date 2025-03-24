package org.example;

import static org.example.utils.CaseUtils.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CaseListTest {
    @DisplayName("测试JDK版本号")
    @Test
    void test0JDKVersion() {
        doTest("usecase.Demo01$JDKVersion", new Object[] {21});
    }

    @DisplayName("JEP394:instanceof表达式增强")
    @Test
    void test1JEP394() {
        doTest("usecase.Demo02$JEP394", assertMethodLineMax(5));
    }

    @DisplayName("JEP361:Switch表达式增强")
    @Test
    void test2JEP361() {
        doTest("usecase.Demo02$JEP361", assertMethodLineMax(10));
    }

    @DisplayName("JEP441:Switch支持按“类型”模式进行匹配")
    @Test
    void test3JEP441() {
        doTest("usecase.Demo02$JEP441", assertMethodLineMax(10));
    }

    @DisplayName("JEP441:__-空指针问题")
    @Test
    void test4JEP441_2() {
        doTest("usecase.Demo02$JEP441_2");
    }

    @DisplayName("JEP441:__-覆盖问题")
    @Test
    void test5JEP441_3() {
        doTest("usecase.Demo02$JEP441_3");
    }

    @DisplayName("JEP395:Record类型")
    @Test
    void test6JEP395() {
        doTest("usecase.Demo02$JEP395", assertClassLineMax(10));
    }

    @DisplayName("JEP440:Record匹配（解构）")
    @Test
    void test7JEP440() {
        doTest("usecase.Demo02$JEP440", assertMethodLineMax(5));
    }

    @DisplayName("JEP440:__-值空问题")
    @Test
    void test7JEP440_2() {
        doTest("usecase.Demo02$JEP440_2");
    }

    @DisplayName("JEP440:__-不匹配问题")
    @Test
    void test7JEP440_3() {
        doTest("usecase.Demo02$JEP440_3");
    }
}
