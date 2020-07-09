package com.wjw.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
    public static List<String> data() {
        List<String> data = new ArrayList<>();
        data.add("交付>会议产品>分摊费用");
        data.add("运维>会议产品>分摊费用");
        data.add("交付>产品>费用");
        data.add("打架>产品>分摊");

        return data;
    }

    /**
     * 每次调用获取当前分类的下一级
     *
     * @param set
     * @param dataList
     */
    public static Map<String, Object> test(Set<String> set, List<List<String>> dataList, int i) {
        Map<String, Object> map = new HashMap<>(16);
        Set<String> set1 = new HashSet<>();
        if (set == null || set.isEmpty()) {
            return map;
        }
        for (String str : set) {
            for (List<String> strings : dataList) {
                if (i < strings.size()-1 && strings.get(i).equals(str)) {
                    set1.add(strings.get(i + 1));
                }
            }
            map.put(str,set1);
        }
        System.out.println(set1);

        return test(set1, dataList, ++i);
    }

    public static void main(String[] args) {
        List<String> data = data();
        Set<String> set = new HashSet<>();

        List<List<String>> dataList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String[] strings = data.get(i).split(">");
            set.add(strings[0]);
            dataList.add(Arrays.asList(strings));
        }

        List<Map<String, Object>> collect = set.stream().map(str -> {
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            map.put(str, test(set, dataList, i));
            return map;
        }).collect(Collectors.toList());
        //递归调用test,获取当前分类的下一级

        //递归返回后的数据放入list数组中

        //返回数组给调用者
        System.out.println("结果为：" + collect);
    }
}
