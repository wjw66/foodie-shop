package com.wjw.impl;

import com.wjw.StuService;
import com.wjw.mapper.StuMapper;
import com.wjw.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : wjwjava01@163.com
 * @date : 17:52 2020/4/25
 * @description :
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public int save(Stu stu) {
        int i = stuMapper.insert(stu);
        if (i > 0){
            return 1;
        }
        return 0;
    }
}
