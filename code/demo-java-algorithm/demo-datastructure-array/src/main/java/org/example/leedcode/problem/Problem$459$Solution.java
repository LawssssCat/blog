package org.example.leedcode.problem;

public class Problem$459$Solution {
    public boolean repeatedSubstringPattern(String s) {
        char[] arr = s.toCharArray();
        int[] next = genNext(arr);
        int len = next.length - (next[next.length - 1] + 1);
        return arr.length % len == 0;
    }

    private int[] genNext(char[] arr) {
        return new Problem$28$Solution.KMS(String.valueOf(arr)).genNext();
    }
    private int[] genNext_bak(char[] arr) {
        int[] next = new int[arr.length];
        if (next.length > 0) {
            next[0] = -1;
        }
        for (int i = 1; i < arr.length; i++) {
            int p = next[i - 1];
            while (p != -1 && arr[p] != arr[i - 1]) {
                p = next[p];
            }
            next[i] = p + 1;
        }
        return next;
    }
}
