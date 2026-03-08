package org.example.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 对象自定义“标准化”
 */
@Slf4j
public class ObjectsTest {
    @Getter
    @Setter
    @AllArgsConstructor
    @FieldNameConstants
    private static class Person implements Comparable<Person> {
        private String name;
        private int age;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .omitNullValues()
                    .add(Fields.name, this.name)
                    .add(Fields.age, this.age)
                    .toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.name, this.age);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Person that = (Person) obj;
            return Objects.equal(this.name, that.name)
                    && Objects.equal(this.age, that.age);
        }

        @Override
        public int compareTo(Person o) {
            return ComparisonChain.start()
                    .compare(this.name, o.name)
                    .compare(this.age, o.age)
                    .result();
        }
    }

    @DisplayName("toString")
    @Test
    void testToString() {
        Person person = new Person(null, 20);
        Assertions.assertEquals("Person{age=20}", person.toString());
        log.info(String.valueOf(person.hashCode()));
    }

    @DisplayName("hashCode")
    @Test
    void testEquals() {
        Person person1 = new Person("name", 20);
        Person person2 = new Person("name", 20);
        Person person3 = new Person("name", 21);
        Assertions.assertTrue(person1.equals(person2));
        Assertions.assertFalse(person1.equals(person3));
        Assertions.assertFalse(person1.equals(null));
    }

    @Test
    void testCompareTo() {
        Person person1 = new Person("name", 20);
        Person person2 = new Person("name", 20);
        Person person3 = new Person("name", 21);
        Assertions.assertTrue(person1.compareTo(person2) == 0);
        Assertions.assertTrue(person1.compareTo(person3) < 0);
    }
}
