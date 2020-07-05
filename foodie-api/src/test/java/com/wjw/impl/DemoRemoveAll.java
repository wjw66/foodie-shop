package com.wjw.impl;

import com.wjw.SpringBootApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author : wjwjava01@163.com
 * @date : 22:48 2020/6/19
 * @description :
 */
@Slf4j
public class DemoRemoveAll extends SpringBootApplicationTest {
    @Test
    public void testRemoveAll() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> removeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        list.removeAll(removeList);
        list.forEach(System.out::print);
    }
}
