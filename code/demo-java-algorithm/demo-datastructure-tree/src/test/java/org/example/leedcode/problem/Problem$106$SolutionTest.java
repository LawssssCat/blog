package org.example.leedcode.problem;

import lombok.extern.slf4j.Slf4j;
import org.example.leedcode.datastructure.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class Problem$106$SolutionTest {

    @Test
    void test01() {
        TreeNode treeNode = new Problem$106$Solution().buildTree(arr("9,3,15,20,7"), arr("9,15,7,20,3"));
        show(treeNode);
    }

    @Test
    void test02() {
        TreeNode treeNode = new Problem$106$Solution().buildTree(arr("2,1"), arr("2,1"));
        show(treeNode);
    }

    private void show(TreeNode treeNode) {
        new Problem$257$Solution().binaryTreePaths(treeNode).stream().map(Object::toString).forEach(log::info);
    }

    private int[] arr(String arr) {
        String[] split = arr.split(",");
        return Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
    }
}