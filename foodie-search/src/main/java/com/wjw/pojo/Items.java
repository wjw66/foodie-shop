package com.wjw.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author : wjwjava01@163.com
 * @date : 17:39 2020/7/19
 * @description :
 */
@Data
@Document(indexName = "foodie-items",type = "doc", createIndex = false)
public class Items {
    //index 设置为false不建立倒排索引
    @Field(store = true,type = FieldType.Text,index = false)
    private String itemId;
    @Field(store = true,type = FieldType.Text,index = true)
    private String itemName;
    @Field(store = true,type = FieldType.Text,index = false)
    private String imgUrl;
    @Field(store = true,type = FieldType.Integer,index = true)
    private Integer price;
    @Field(store = true,type = FieldType.Integer,index = true)
    private Integer sellCounts;
}
