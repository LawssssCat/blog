package usecase;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo02$JEP395 {
    class Record {
        private String value;

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Record record = (Record)o;
            return Objects.equals(value, record.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Record{" +
                "value='" + value + '\'' +
                '}';
        }
    }

    // @Data
    // class Record {
    // private String value;
    // }
    //
    // record Record(String value) {
    // }

    public void test() {}
}
