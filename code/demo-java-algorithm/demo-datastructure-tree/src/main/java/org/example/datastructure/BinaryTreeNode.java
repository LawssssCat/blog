package org.example.datastructure;

import lombok.Data;

@Data
public class BinaryTreeNode {
    int val = 0;
    BinaryTreeNode left = null;
    BinaryTreeNode right = null;
    public BinaryTreeNode(int val) {
        this.val = val;
    }
}
