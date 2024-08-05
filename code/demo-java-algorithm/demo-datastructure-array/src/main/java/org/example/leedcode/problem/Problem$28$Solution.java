package org.example.leedcode.problem;

public class Problem$28$Solution {
    public int strStr(String haystack, String needle) {
        return new KMS(needle).indexOf(haystack);
    }

    public static class KMS {
        private char[] arr;
        private int[] next;
        public KMS(String needle) {
            this.arr = needle.toCharArray();
            this.next = genNext();
            toNextVal();
        }

        int[] genNext() {
            int[] next = new int[this.arr.length];
            if (next.length > 0) {
                next[0] = -1;
            }
            for (int i=1; i<next.length; i++) {
                int p = next[i-1];
                while(p!=-1 && this.arr[i-1] != this.arr[p]) {
                    p = next[p];
                }
                next[i] = p + 1;
            }
            return next;
        }

        void toNextVal() {
            for(int i=1; i<this.next.length; i++) {
                int p = this.next[i];
                if (this.arr[p] == this.arr[i]) {
                    this.next[i] = this.next[p];
                }
            }
        }

        public int indexOf(String str) {
            char[] xxx = str.toCharArray();
            for(int i=0, j=0; i<xxx.length;) {
                while(j<this.arr.length && i<xxx.length && xxx[i] == this.arr[j]) {
                    i++;
                    j++;
                }
                if (j == this.arr.length) {
                    return i - j;
                }
                if (j == 0) {
                    i++;
                    continue;
                }
                j = this.next[j];
                if (j==-1) {
                    j=0;
                }
            }
            return -1;
        }
    }
}
