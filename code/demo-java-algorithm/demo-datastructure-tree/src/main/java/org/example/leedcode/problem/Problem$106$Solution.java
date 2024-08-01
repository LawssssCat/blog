package org.example.leedcode.problem;

import org.example.leedcode.datastructure.TreeNode;

/**
 * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 */
public class Problem$106$Solution {
    /**
     * @param inorder   中序，确定范围
     * @param postorder 后序，确定根节点
     * @return 节点
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return xx(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }

    private TreeNode xx(int[] inorder, int[] postorder, int il, int ir, int pl, int pr) {
        if (!inRange(0, il, ir, inorder.length - 1) || !inRange(0, pl, pr, postorder.length - 1)) {
            return null;
        }
        int val = postorder[pr];
        TreeNode root = new TreeNode(val);
        int ii = indexOf(inorder, il, ir, val);
        int sizeL = ii - il;
        // left
        root.left = xx(inorder, postorder, il, ii - 1, pl, pl + sizeL - 1);
        // right
        root.right = xx(inorder, postorder, ii + 1, ir, pl + sizeL, pr - 1);
        return root;
    }

    private int indexOf(int[] arr, int l, int r, int v) {
        for (int i=l; i<=r; i++) {
            if (arr[i] == v) {
                return i;
            }
        }
        return -1;
    }

    private boolean inRange(int... vs) {
        int pre = vs[0];
        for (int i = 1; i < vs.length; i++) {
            int next = vs[i];
            if (pre > next) {
                return false;
            }
            pre = next;
        }
        return true;
    }
}
