package org.example.leedcode.problem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem$28$SolutionTest {
    @Test
    void test() {
        assertFunc("mississippi", "issip");
    }

    private void assertFunc(String haystack, String needle) {
        int val = new Problem$28$Solution().strStr(haystack, needle);
        assertEquals(haystack.indexOf(needle), val);
    }
}