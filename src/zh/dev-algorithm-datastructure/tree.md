---
title: 树（Tree）
order: 11
---

## 遍历

:::::: tabs

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-tree/src/test/java/org/example/test1/BinaryTreeTraversalTest.java -->
```

@tab 实现

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-tree/src/main/java/org/example/util/BinaryTreeTraversalUtil.java -->
```

::::::

相关例题：DFS

- [LeetCode：222.完全二叉树节点的数量](https://leetcode.cn/problems/count-complete-tree-nodes/description/) （有特解）
- [LeetCode：110.平衡二叉树](https://leetcode.cn/problems/balanced-binary-tree/)
- [LeetCode：257. 二叉树的所有路径](https://leetcode.cn/problems/binary-tree-paths/description/)
- [LeetCode：112. 路径总和](https://leetcode.cn/problems/path-sum/description/)
- [LeetCode：106.从中序与后序遍历序列构造二叉树](https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/) （逆向生成树，KMP 算法）

相关例题：BFS

- [LeetCode：513. 找树左下角的值](https://leetcode.cn/problems/find-bottom-left-tree-value/description/) （层序、先序均可）

:::: tip
在完全遍历的情况下，解题方式中，DFS 效率优于 BFS （猜测：不用创建内存的缘故把）
::::

### 深度优先遍历（DFS）

概要

- 前序：中左右
- 中序：左中右
- 后序：左右中

#### 递归法

```java
/**
 * 前序：中左右
 */
void traversal(cur, vec) {
  if (cur == null) {
    return;
  }
  vec.push(cur->val); // 中
  traversal(cur->left, vec); // 左
  traversal(cur->right, vec); // 右
}
```

#### 非递归法

todo pseudo code

```java
// 前序：中左右
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
```

### 层序遍历（广度优先，BFS）【重要】

```java
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
```

## 问题：深度

相关例题：

- [LeetCode：104.二叉树的最大深度](https://leetcode.cn/problems/maximum-depth-of-binary-tree/solutions/)
- [LeetCode：111.二叉树的最小深度](https://leetcode.cn/problems/minimum-depth-of-binary-tree/)

easy

## 问题：反转

相关例题：

- [LeetCode：226.翻转二叉树](https://leetcode.cn/problems/invert-binary-tree/description/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode l = invertTree(root.left);
        TreeNode r = invertTree(root.right);
        root.left = r;
        root.right = l;
        return root;
    }
}
```

## 问题：判断是否对称

相关问题：

- [LeetCode：101. 对称二叉树](https://leetcode.cn/problems/symmetric-tree/)

双指针

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return xx(root.left, root.right);
    }
    boolean xx(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;
        return xx(left.left, right.right) && xx(left.right, right.left);
    }
}
```
