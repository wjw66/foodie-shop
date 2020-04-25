package com.wjw.my.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author : wjwjava01@163.com
 * @date : 12:49 2020/4/25
 * @description :
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
