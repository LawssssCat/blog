package org.example.util;

import org.example.datastructure.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BinaryTreeTraversalUtil {
    /**
     * 前序
     */
    public static void dfsBAC(BinaryTreeNode root, Consumer<Integer> func) {
        if (root == null) {
            return;
        }
        func.accept(root.getVal());
        dfsBAC(root.getLeft(), func);
        dfsBAC(root.getRight(), func);
    }

    /**
     * 前序
     * ❗非递归
     */
    public static void dfsBAC2(BinaryTreeNode root, Consumer<Integer> func) {
        BinaryTreeNode cur = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        while(cur!=null || !stack.isEmpty()) {
            if (cur != null) {
                func.accept(cur.getVal());
                stack.push(cur.getRight());
                stack.push(cur.getLeft());
            }
            cur = stack.pop();
        }
    }

    /**
     * 中序
     */
    public static void dfsABC(BinaryTreeNode root, Consumer<Integer> func) {
        if (root == null) {
            return;
        }
        dfsABC(root.getLeft(), func);
        func.accept(root.getVal());
        dfsABC(root.getRight(), func);
    }

    /**
     * 中序
     * ❗非递归
     */
    public static void dfsABC2(BinaryTreeNode root, Consumer<Integer> func) {
        BinaryTreeNode cur = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        while(cur!=null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.getLeft();
                continue;
            }
            cur = stack.pop();
            func.accept(cur.getVal());
            cur = cur.getRight();
        }
    }

    /**
     * 后序
     */
    public static void dfsACB(BinaryTreeNode root, Consumer<Integer> func) {
        if (root == null) {
            return;
        }
        dfsACB(root.getLeft(), func);
        dfsACB(root.getRight(), func);
        func.accept(root.getVal());
    }

    /**
     * 后序：左右中
     * ❗非递归
     */
    public static void dfsACB2(BinaryTreeNode root, Consumer<Integer> func) {
        BinaryTreeNode cur = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        Stack<BinaryTreeNode> callback = new Stack<>();
        while(cur!=null || !stack.isEmpty()) {
            if (cur != null) {
                callback.push(cur); // 💡用于反转： 中右左 -> 左右中（后序）
                stack.push(cur.getLeft());
                stack.push(cur.getRight());
            }
            cur = stack.pop();
        }
        // callback
        while (!callback.isEmpty()) {
            BinaryTreeNode pop = callback.pop();
            func.accept(pop.getVal());
        }
    }

    public static void bfs(BinaryTreeNode root, BiConsumer<Integer, Integer> func) {
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        int size = 1; // ❗记录当前层数量
        int level = 1;
        queue.add(root);
        while (!queue.isEmpty()) {
            while(size > 0) {
                size--;
                BinaryTreeNode cur = queue.poll();
                func.accept(level, cur == null ? null : cur.getVal());
                if (cur == null) {
                    continue;
                }
                queue.add(cur.getLeft());
                queue.add(cur.getRight());
            }
            size = queue.size();
            level++;
        }
    }

    /**
     * 1,2,3,4,#,5,6
     * 1,2
     */
//    public BinaryTreeNode fromString(String str) {
//        Iterable<String> split = Splitter.on(',').trimResults().omitEmptyStrings().split(str);
//    }
}
