package org.example.test1;

import org.example.datastructure.BinaryTreeNode;
import org.example.util.BinaryTreeTraversalUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BinaryTreeTraversalTest {
    @Test
    void test() {
        /*
          3
         2  5
        1  4  7
             6
         */
        BinaryTreeNode root = new BinaryTreeNode(3);
        {
            BinaryTreeNode l = new BinaryTreeNode(2);
            BinaryTreeNode r = new BinaryTreeNode(5);
            BinaryTreeNode ll = new BinaryTreeNode(1);
            BinaryTreeNode rl = new BinaryTreeNode(4);
            BinaryTreeNode rr = new BinaryTreeNode(7);
            BinaryTreeNode rrl = new BinaryTreeNode(6);
            root.setLeft(l);
            root.setRight(r);
            l.setLeft(ll);
            r.setLeft(rl);
            r.setRight(rr);
            rr.setLeft(rrl);
        }
        // 前序
        {
            List<Integer> v1 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsBAC(root, (value) -> {
                v1.add(value);
            });
            log.info("前序: {}", v1);
            // 非递归
            List<Integer> v2 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsBAC2(root, (value) -> {
                v2.add(value);
            });
            Assertions.assertArrayEquals(v1.toArray(), v2.toArray());
        }
        // 中序
        {
            List<Integer> v1 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsABC(root, (value) -> {
                v1.add(value);
            });
            log.info("中序: {}", v1);
            // 非递归
            ArrayList<Integer> v2 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsABC2(root, (value) -> {
                v2.add(value);
            });
            Assertions.assertArrayEquals(v1.toArray(), v2.toArray());
        }
        // 后序
        {
            List<Integer> v1 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsACB(root, (value) -> {
                v1.add(value);
            });
            log.info("后序: {}", v1);
            // 非递归
            ArrayList<Integer> v2 = new ArrayList<>();
            BinaryTreeTraversalUtil.dfsACB2(root, (value) -> {
                v2.add(value);
            });
            Assertions.assertArrayEquals(v1.toArray(), v2.toArray());
        }
        // 层序遍历
        {
            List<List<Integer>> tree = new ArrayList<>();
            BinaryTreeTraversalUtil.bfs(root, (level, val) -> {
                if (tree.size() > level-1) {
                    tree.get(level-1).add(val);
                } else {
                    List<Integer> flow = new ArrayList<>();
                    flow.add(val);
                    tree.add(flow);
                }
            });
            // show
            for (int i = 0; i < tree.size(); i++) {
                List<String> flow = tree.get(i).stream()
                        .map(v -> v==null ? "#" : String.valueOf(v))
                        .collect(Collectors.toList());
                log.info("{}: {}", i, flow);
            }
        }
    }
}
