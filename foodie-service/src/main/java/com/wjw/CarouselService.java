package com.wjw;

import com.wjw.pojo.Carousel;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 13:00 2020/5/5
 * @description : 首页轮播图service
 */
public interface CarouselService {
    /**
     * 查询所有轮播图列表
     * @param isShow 是否显示
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
