package org.example.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class KMSTest {
    @Test
    void test() {
        assertEquals(0, "xxx".indexOf(""));
    }

    @Test
    void test00() {
        char[] arr = "".toCharArray();
        int[] expectNext = new int[]{};
        int[] expectNextVal = new int[]{};
        assertNextFunc(arr, expectNext, expectNextVal);
        assertIndexOf(arr, "ababac");
    }

    @Test
    void test01() {
        char[] arr = "ababac".toCharArray();
        int[] expectNext = new int[]{-1, 0, 0, 1, 2, 3};
        int[] expectNextVal = new int[]{-1, 0, -1, 0, -1, 3};
        assertNextFunc(arr, expectNext, expectNextVal);
        assertIndexOf(arr, "ababac");
        assertIndexOf(arr, "aababac");
        assertIndexOf(arr, "abababac");
        assertIndexOf(arr, "abababacf");
        assertIndexOf(arr, "ababa-bacf");
    }

    @Test
    void test02() {
        char[] arr = "aabaaf".toCharArray();
        int[] expectNext = new int[]{-1, 0, 1, 0, 1, 2};
        int[] expectNextVal = new int[]{-1, -1, 1, -1, -1, 2};
        assertNextFunc(arr, expectNext, expectNextVal);
    }

    @Test
    void test03() {
        char[] arr = "abababab".toCharArray();
        int[] expectNext = new int[]{-1, 0, 0, 1, 2, 3, 4, 5};
        int[] expectNextVal = new int[]{-1, 0, -1, 0, -1, 0, -1, 0};
        assertNextFunc(arr, expectNext, expectNextVal);
    }

    private void assertNextFunc(char[] arr, int[] expectNext, int[] expectNextVal) {
        KMS kms = new KMS();
        int[] next = kms.genNext(arr);
        log.info("next: {}", next);
        assertArrayEquals(expectNext, next);
        kms.toNextVal(arr, next);
        log.info("next: {}", next);
        assertArrayEquals(expectNextVal, next);
    }

    private void assertIndexOf(char[] arr, String str) {
        Integer indexOf = str.indexOf(String.valueOf(arr));
        assertEquals(indexOf, new KMS().indexOf(arr, str.toCharArray()));
    }
}