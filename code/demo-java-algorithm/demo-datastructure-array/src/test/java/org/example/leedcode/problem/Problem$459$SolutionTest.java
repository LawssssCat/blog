package org.example.leedcode.problem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Problem$459$SolutionTest {
    @Test
    void test() {
        assertFunc("abab", true);
        assertFunc("aaa", true);
        assertFunc("aaaaa", true);
        assertFunc("aba", false);
        assertFunc("abcabcabcabc", true);
        assertFunc("aabaaba", false);
        assertFunc("abac", false);
    }

    private void assertFunc(String str, boolean expect) {
        boolean result = new Problem$459$Solution().repeatedSubstringPattern(str);
        assertEquals(expect, result);
    }
}