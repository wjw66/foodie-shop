package com.wjw.newtree;

import java.util.*;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/10 9:06
 **/
public class TreeDemo {
    public static void main(String[] args) {

    }
    /**
     * 获取当前节点的子节点set集合
     * @param key 所有父节点和当前节点拼接成的字符串
     * @param height 当前树的高度,从1开始
     * @return set集合,记录子节点所有元素
     */
    public static Set<String> getChild(String key, int height) {
        List<List<String>> dataList = Demo.data();

        Map<String, Set<String>> map = new HashMap<>(16);
        Set<String> set = new HashSet<>();

        for (List<String> list : dataList) {
            StringBuilder parentKey = new StringBuilder();
            //拼接从第一个元素-当前元素作为key
            for (int i = 0; i < height; i++) {
                parentKey.append(list.get(i));
            }
            //找到符合条件的元素就将其放入set集合中(去重)
            if (parentKey.toString().equals(key)) {
                set.add(list.get(height));
                map.put(parentKey.toString(), set);
            }
        }
        //返回子节点的所有元素
        return map.get(key);
    }
}
