package org.example.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验注解：判断是 ip or domain or host
 */
@Documented
@Constraint(validatedBy = { MyUrlValidator.class })
@Target({ FIELD, TYPE_USE })
@Retention(RUNTIME)
public @interface MyUrl {
    String message() default "请输入正确的地址";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
