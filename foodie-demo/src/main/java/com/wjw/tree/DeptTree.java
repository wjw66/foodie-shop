package com.wjw.tree;

/**
 * @author : wjwjava01@163.com
 * @date : 23:54 2020/7/9
 * @description :
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {
    private String name;

    public List<DeptTree> getDeptTree(List<SysDept> depts) {
        List<DeptTree> treeList = depts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .map(dept -> {
                    DeptTree node = new DeptTree();
                    node.setId(Integer.parseInt(dept.getDeptId()));
                    node.setParentId(Integer.parseInt(dept.getParentId()));
                    node.setName(dept.getName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.bulid(treeList, 0);
    }
}
