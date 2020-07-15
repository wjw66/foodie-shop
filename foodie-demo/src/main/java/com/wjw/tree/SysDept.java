package com.wjw.tree;

import lombok.Data;

/**
 * @author : wjwjava01@163.com
 * @date : 23:56 2020/7/9
 * @description :
 */
@Data
public class SysDept {
    protected String deptId;
    protected String parentId;
    protected String name;
}
