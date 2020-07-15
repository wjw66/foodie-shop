package com.wjw.newtree;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : wjwjava01@163.com
 * @date : 0:54 2020/7/10
 * @description :
 */
@Data
public class TreeNode {
    /**
     * 当前节点的value
     */
    private String nodeValue;
    /**
     * 当前节点的子节点集合
     */
    private Set<TreeNode> children = new HashSet<>();

    public void add(TreeNode node) {
        children.add(node);
    }

    public TreeNode(String nodeValue, Set<TreeNode> children) {
        this.nodeValue = nodeValue;
        this.children = children;
    }

    public TreeNode() {
    }
}
