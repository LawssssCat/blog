package org.example.leedcode.problem;

import lombok.extern.slf4j.Slf4j;
import org.example.leedcode.datastructure.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class Problem$257$SolutionTest {
    @Test
    void test01() {
        TreeNode n5 = new TreeNode(5);
        TreeNode n2 = new TreeNode(2, null, n5);
        TreeNode n3 = new TreeNode(3);
        TreeNode n1 = new TreeNode(1, n2, n3);
        // test
        List<String> paths = new Problem$257$Solution().binaryTreePaths(n1);
        // assert
        Assertions.assertArrayEquals(new String[] {
                "1->2->5",
                "1->3"
        }, paths.toArray());
    }
}
