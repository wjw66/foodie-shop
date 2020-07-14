package com.wjw.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/14 14:01
 * es stu搜索
 **/
@Data
@Document(indexName = "stu",type = "_doc")
public class Stu implements Serializable {
    private static final long serialVersionUID = 7174984162241719947L;
    @Id
    private Long stuId;
    @Field(store = true,type = FieldType.Text)
    private String name;
    @Field(store = true,type = FieldType.Integer)
    private Integer age;
//    @Field(store = true,type = FieldType.Text)
//    private String tags;
//    @Field(store = true,type = FieldType.Text)
//    private String desc;
}
