package com.wjw.newtree;

import java.util.*;

/**
 * @author : wjwjava01@163.com
 * @date : 0:57 2020/7/10
 * @description : 实现了业务需求,但是还有优化的空间(代码会更加复杂),时间不够不写了...
 */
public class Demo {
    /**
     * 实现懒加载,点击当前层会返回下一层所有的元素(如果不懒加载可以循环遍历所有key)
     * @param args
     */
    public static void main(String[] args) {
        Set<String> child = getChild("交付", 1);
        System.out.println(child);
    }
    /**
     * 获取当前节点的子节点set集合
     * @param key 所有父节点和当前节点拼接成的字符串
     * @param height 当前树的高度,从1开始
     * @return set集合,记录子节点所有元素
     */
    public static Set<String> getChild(String key, int height) {
        List<List<String>> dataList = data();

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
    public static List<List<String>> data() {
        List<String> data = new ArrayList<>();
        data.add("交付>会议产品>分摊费用");
        data.add("运维>会议产品>分摊费用");
        data.add("交付>产品>费用");
        data.add("打架>产品>分摊");

        List<List<String>> dataList = new ArrayList<>();
        for (String datum : data) {
            String[] strings = datum.split(">");
            dataList.add(Arrays.asList(strings));
        }
        return dataList;
    }
}
