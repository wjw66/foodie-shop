package com.wjw.impl;

import com.wjw.SpringBootApplicationTest;
import com.wjw.StuService;
import com.wjw.pojo.Stu;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wjwjava01@163.com
 * @date : 17:53 2020/4/25
 * @description :
 */

public class StuServiceImplTest extends SpringBootApplicationTest {
    @Autowired
    private StuService StuService;

    @Test
    public void save() {
        Stu stu = new Stu();
        stu.setId(10086);
        stu.setName("狗蛙");
        stu.setAge(18);

        int i = StuService.save(stu);
        Assert.assertEquals(1,i);
    }
}