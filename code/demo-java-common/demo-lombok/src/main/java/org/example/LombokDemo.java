package org.example;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
// @XSlf4j
// @Log
// @Log4j
// @Log4j2
// @CommonsLog
// @JBossLog
public class LombokDemo {
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"name", "sex"}) // 指定参与 equals 和 hashcode 的字段
    @ToString(of = {"name", "sex"}) // 同上
    private static class Student01 {
        private String name;
        private Integer age;
        private Byte sex;

        public static void main(String[] args) {
            Student01 student01_1 = new Student01();
            student01_1.setName("hello"); // set
            student01_1.setAge(1);
            student01_1.getName(); // get
            Student01 student01_2 = new Student01();
            student01_2.setName("hello");
            student01_2.setAge(3);
            // equals
            assert student01_1.equals(student01_2);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE) // 无参构造函数，加 private 访问限制
    @AllArgsConstructor
    @FieldNameConstants // 获取类属性名
    private static class Student02 {
        private String name;
        private Integer age;

        public static void main(String[] args) {
            new Student02();
            new Student02("hello", 2);
            log.info(Student02.Fields.age); // print "age"
        }
    }

    @Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    @Accessors(
            // 链式调用
            chain = true, // 将 setXxxx 方法返回值设为当前对象 e.g. setXxx(String name) {o.name = name; return o;}
            fluent = true // 将 setXxxx 方法名改为 Xxxx
    )
    private static class Student03 {
         private String name;
         private Integer age;

        public static void main(String[] args) {
            new Student03().name("hello").age(2);
        }
    }

    @Value // @Getter @FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE) @AllArgsConstructor @ToString @EqualsAndHashCode
    private static final class Student03_2 { // @Value 已将类标记为 final。
        private String name; // @Value 已将非 static 的 package-local 字段标记为 private。
        private Integer age;

        public static void main(String[] args) {
            Student03_2 student03 = new Student03_2("hello", 2);
            student03.getName(); // get ok
            // student03.setName("hello"); // set miss
        }
    }

    @Builder
    private static class Student04 {
        private String name;
        private Double age;
        @Singular("addHobby")
        private List<String> hobby;

        public static void main(String[] args) {
            Student04 student04 = Student04.builder()
                    .name("ikun")
                    .age(2.5)
                    .addHobby("basketball")
                    .addHobby("sing")
                    .addHobby("skip")
                    .addHobby("rap")
                    .build();
        }
    }

    @SneakyThrows
    public void shitHappens() {
        Thread.sleep(1000);
    }

    @Synchronized
    public void concurrent() {
        // public synchronized void concurrent() {}
    }

    // 主动关闭资源
    public void copyFile(String in, String out) throws IOException {
        @Cleanup FileInputStream inStream = new FileInputStream(in);
        @Cleanup FileOutputStream outStream = new FileOutputStream(out);
        byte[] b = new byte[65536];
        int r;
        while ((r = inStream.read(b)) != -1) {
            outStream.write(b, 0, r);
        }
    }
}
