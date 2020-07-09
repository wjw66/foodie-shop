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
    private String nodeValue;
    private Set<TreeNode> children = new HashSet<>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
