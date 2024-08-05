package org.example.string;

public class KMS {
    /**
     * 求 next 数组
     * （最长公共前后缀）
     *
     * @param arr 源
     * @return next 数组
     */
    int[] genNext(char[] arr) {
        int[] next = new int[arr.length];
        if (next.length > 0) {
            next[0] = -1; // 初始化，固定值
        }
        for (int i = 1; i < next.length; i++) { // 当前位
            int p = next[i - 1]; // 前位next
            while (p != -1 && arr[p] != arr[i - 1]) { // 前位值 vs 当前位值
                p = next[p]; // 前位prev
            }
            next[i] = p + 1;
        }
        return next;
    }

    /**
     * next to nextVal
     * （提前处理好迭代，便于 indexOf 计算）
     *
     * @param arr 源
     * @param next 数组 to nextVal
     */
    void toNextVal(char[] arr, int[] next) {
        for (int i = 1; i < arr.length; i++) {
            int p = next[i];
            if(arr[i] == arr[p]) {
                next[i] = next[p];
            }
        }
    }

    /**
     * 相同子串开始下标
     *
     * @param str 子串
     * @param context 待遍历字符串
     * @return 相同子串开始下标
     */
    public int indexOf(char[] str, char[] context) {
        int[] next = genNext(str);
        toNextVal(str, next);
        for (int i = 0, j = 0; i < context.length;) {
            while (j < str.length && i < context.length && str[j] == context[i]) {
                j++;
                i++;
            }
            if (j == str.length) {
                return i - j;
            }
            if (j==0) {
                i++;
                continue;
            }
            // next
            j = next[j];
            if (j == -1) {
                j = 0;
            }
        }
        return -1;
    }
}
