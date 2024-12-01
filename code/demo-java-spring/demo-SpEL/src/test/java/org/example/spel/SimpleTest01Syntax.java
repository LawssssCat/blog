package org.example.spel;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpEL 基本使用 —— 语法解析
 */
@Slf4j
public class SimpleTest01Syntax {
    ExpressionParser parser = new SpelExpressionParser(); // 指定 SpEL 解析器

    void show(String msg, Object result) {
        log.info("{} = {} ({})", msg, result, result.getClass());
    }

    /**
     * 类型识别
     */
    @Test
    void testResultType() {
        // 默认：自动转换返回值
        {
            TriConsumer<String, Object, Class<?>> func = (expression, expect, clazz) -> {
                Object result = parser.parseExpression(expression).getValue();
                show(expression, result);
                assertEquals(expect, result);
                assertInstanceOf(clazz, result);
            };
            func.accept("10 + 20", 30, Integer.class);
            func.accept("1.1E10", 1.1E10, Double.class);
            func.accept("true", true, Boolean.class);
        }
        // 显式指定返回值
        {
            TriConsumer<String, Object, Class<?>> func = (expression, expect, clazz) -> {
                Object result = parser.parseExpression(expression).getValue(new StandardEvaluationContext(), clazz);
                show(expression, result);
                assertEquals(expect, result);
                assertInstanceOf(clazz, result);
            };
            func.accept("10 + 20", "30", String.class);
            func.accept("1.1E10", 1.1E10, Double.class);
        }
    }

    /**
     * 范围指定
     */
    @Test
    void testParseScope() {
        // 语法解析失败
        {
            String msg = "hello 10 + 20";
            assertThrowsExactly(SpelParseException.class, () -> {
                Expression expression = parser.parseExpression(msg);
            });
        }
        // 范围指定（默认）
        {
            String msg = "hello #{10 + 20}";
            Expression expression = parser.parseExpression(msg, ParserContext.TEMPLATE_EXPRESSION);
            Object result = expression.getValue(new StandardEvaluationContext());
            show(msg, result);
            assertEquals("hello 30", result);
            assertInstanceOf(String.class, result);
        }
        // 范围指定（自定义）
        {
            String msg = "hello #[10 + 20]"; // 💡自定义语法
            Expression expression = parser.parseExpression(msg, new ParserContext() {
                @Override
                public boolean isTemplate() {
                    return true;
                }
                @Override
                public String getExpressionPrefix() {
                    return "#[";
                }
                @Override
                public String getExpressionSuffix() {
                    return "]";
                }
            });
            Object result = expression.getValue(new StandardEvaluationContext());
            show(msg, result);
            assertEquals("hello 30", result);
            assertInstanceOf(String.class, result);
        }
    }

    /**
     * 运算
     */
    @Test
    void testCalculate() {
        BiConsumer<String, Object> func = (expression, expect) -> {
            Object result = parser.parseExpression(expression).getValue();
            show(expression, result);
            assertEquals(expect, result);
        };
        // 数学计算
        {
            // 加减 + - add ...
            func.accept("1 + 1", 2);
            // 乘除 * / div ...
            func.accept("10/3", 3);
            func.accept("10 div 3", 3);
            func.accept("10 / 3.0", 10 / 3.0000000000000000000001);
            // 取模 % mod
            func.accept("10 % 3", 1);
            func.accept("10 mod 3", 1);
            // 指数 ^
            func.accept("1^1000000", 1);
        }
        // 逻辑判断
        {
            /*
            10 == 10
            10 EQ 10 —— 加法优先级高，so 10 + 20 eq 30 = true
            10 != 10
            10 NE 10
            10 > 10
            10 GT 10
            10 >= 10
            10 GE 10
            10 < 10
            10 LT 10
            10 <= 10
            10 LE 10
            10 BETWEEN {5,20}
             */
        }
        // 关系表达式
        {
            /*
            与 and, &&
            或 or, ||
            非 not(...)
             */
        }
        // 三目运算
        {
            /*
            💡表达式结果不允许为空，否则报错
            1 > 2 ? 'hello' : \"world\"
            💡表达式允许为空，空则 false 结果，非空则表达式结果 #groovy表达式
            null ?: 'hello world'            = hello world —— 空则 false 结果
            'hello world' ?: 'good'          = hello world —— 非空则表达式结果
             */
        }
    }

    /**
     * 对象
     */
    @Test
    void testClass() {
        BiConsumer<Object, String> func =  (expect, expression) -> {
            assertEquals(expect, parser.parseExpression(expression).getValue());
        };
        // 获取 Class
        {
            func.accept(String.class, "T(java.lang.String)");
            func.accept(Date.class, "T(java.util.Date)");
        }
        // 静态属性、静态方法
        {
            func.accept(Integer.MAX_VALUE, "T(Integer).MAX_VALUE");
            func.accept(999, "T(Integer).parseInt('999')");
        }
        // 对象实例化
        {
            func.accept(99, "new Integer('99')");
        }
        // instanceof
        {
            func.accept(true, "'hello world' instanceof T(String)");
        }
    }

    /**
     * 变量
     */
    @Test
    void testVariable() {
        // 根变量、常规变量
        {
            Expression expression = parser.parseExpression("#varA + #root + #varB");
            // 💡根变量
            StandardEvaluationContext context = new StandardEvaluationContext(" ");
            // 💡常规变量
            context.setVariable("varA", "hello");
            context.setVariable("varB", "world");
            String value = expression.getValue(context, String.class);
            assertEquals("hello world", value);
        }
        // 方法调用（反射）
        {
            Expression expression = parser.parseExpression("#convert('999')");
            StandardEvaluationContext context = new StandardEvaluationContext();
            try {
                context.setVariable("convert", Integer.class.getMethod("parseInt", String.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Object value = expression.getValue(context);
            assertEquals(999, value);
            assertInstanceOf(Integer.class, value);
        }
        // get/set 方法
        {
            Expression expression = parser.parseExpression("#root.time"); // 💡相当于 getTime() 方法调用
            Date date = new Date();
            StandardEvaluationContext context = new StandardEvaluationContext(date);
            Object value = expression.getValue(context);
            assertEquals(date.getTime(), value);
        }
        // 表达式空处理 #groovy表达式
        {
            // EL1007E: Property or field 'time' cannot be found on null
            assertThrowsExactly(SpelEvaluationException.class, () -> {
                parser.parseExpression("#root.time").getValue();
            });
            // 如果为空，则返回 null
            assertNull(parser.parseExpression("#root?.time").getValue());
        }
        // map
        {
            Map<String, Object> values = Maps.newHashMap();
            values.put("var1", "hello world!");
            values.put("var2", "good");
            values.put("var3", "well");
            // get
            {
                Object value = parser.parseExpression("#root['var2']").getValue(values);
                assertEquals("good", value);
            }
            // set
            {
                Object value = parser.parseExpression("#root['varNew'] = 1").getValue(values);
                assertEquals(1, value);
                assertEquals(1, values.get("varNew")); // 💡新值
            }
            // 迭代
            {
                Expression expression = parser.parseExpression("#root.![#this.key + '-' + #this.value]"); // same as #root.![key + '-' + value]
                Object value = expression.getValue(values);
                show(expression.getExpressionString(), value);
                assertInstanceOf(List.class, value); // map to list
            }
            // 过滤
            {
                Expression expression = parser.parseExpression("#root.?[#this.key.contains('var1')].![key + '-' + value]");
                Object value = expression.getValue(values);
                show(expression.getExpressionString(), value);
                assertInstanceOf(List.class, value); // map to list
            }
        }
    }
}
