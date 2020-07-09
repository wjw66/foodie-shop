package com.wjw.tree;

/**
 * @author : wjwjava01@163.com
 * @date : 23:53 2020/7/9
 * @description :
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {
    protected int id;
    protected int parentId;
    protected String name;
    protected List<TreeNode> children = new ArrayList<>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
