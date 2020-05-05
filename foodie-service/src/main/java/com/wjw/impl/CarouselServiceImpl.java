package com.wjw.impl;

import com.wjw.CarouselService;
import com.wjw.mapper.CarouselMapper;
import com.wjw.pojo.Carousel;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 19:48 2020/5/5
 * @description :
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    @Resource
    private CarouselMapper carouselMapper;
    /**
     * 查询所有轮播图列表
     *
     * @param isShow 是否显示
     * @return
     */
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        //封装example
        Example example = new Example(Carousel.class);
        //设置排序
        example.orderBy("sort").desc();
        //设置查询条件
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);

        return carouselMapper.selectByExample(example);
    }

}
