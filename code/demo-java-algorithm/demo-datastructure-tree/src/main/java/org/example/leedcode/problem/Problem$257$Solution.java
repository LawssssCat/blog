package org.example.leedcode.problem;

import org.example.leedcode.datastructure.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Problem$257$Solution {
    final static String XXX = "->";

    StringBuilder queue = new StringBuilder();
    List<String> result = new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return result;
        }
        String v = String.valueOf(root.val);;
        queue.append(XXX).append(v);
        if (root.left == null && root.right == null) {
            String path = (String) queue.substring(XXX.length());
            result.add(path);
        } else {
            binaryTreePaths(root.left);
            binaryTreePaths(root.right);
        }
        queue.delete(queue.length()-XXX.length()-v.length(), queue.length());
        return result;
    }
}
