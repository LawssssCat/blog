package org.example.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class UserTest {
    // 线程安全
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 对比预期
     *
     * @param user 被测试类
     * @param checkList 有问题的检查项
     * @param groups 检查组
     */
    private void check(User user, Collection<String> checkList, Class<?> ... groups) {
        Set<ConstraintViolation<User>> validate = validator.validate(user, groups);
        // 返回校验不通过的项目
        Set<String> collect = validate.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());

        try {
            Assertions.assertEquals(checkList.size(), collect.size());
            for (String p : checkList) {
                boolean contains = collect.contains(p);
                Assertions.assertTrue(contains, String.format("contains not %s in %s", p, collect));
            }
        } catch (AssertionFailedError e) {
            String allValidateResult = validate.stream().reduce(new StringBuilder("all validate results: \n"),
                    StringBuilder::append,
                    (a, b) -> b).toString();
            log.error("{}", allValidateResult);
            throw e;
        }
    }

    /**
     * 默认校验
     */
    @Test
    void testDefault() {
        User user = User.builder()
                .age(18)
                .birthDay(LocalDateTime.now())
                .phone("")
                .email("xxx@gmail.com")
                .urls(Arrays.asList(
                        "http://example.org",
                        "https://www.example.org:8080/blog/index.jsp#hello-world"
                ))
                .build();
        List<String> checklist = Collections.singletonList("phone");
        check(user, checklist);
    }

    /**
     * 更新情况校验（不包含默认校验）
     */
    @Test
    void testUpdate() {
        User user = User.builder().build();
        List<String> checklist = Collections.singletonList("id");
        check(user, checklist, User.Update.class);
    }

    /**
     * 测试快速失败
     */
    @Test
    void testFailFast() {
        User user = User.builder()
                .id(null)
                .username("")
                .email("xxx")
                .phone("")
                .urls(Arrays.asList(
                        "!!!"
                ))
                .build();

        Validator failFastValidator = Validation
                .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory() // 配置快速失败
                .getValidator();
        Set<ConstraintViolation<User>> validate = failFastValidator.validate(user, User.Insert.class, User.Update.class, Default.class);
        Assertions.assertEquals(1, validate.size()); // 因为快速失败，所有有且只有一个错误信息
    }
}
