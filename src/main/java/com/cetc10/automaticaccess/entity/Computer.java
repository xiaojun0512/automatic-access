package com.cetc10.automaticaccess.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "computer")
@Data
@ApiModel(value = "电脑")
public class Computer {
    @Id
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "价格")
    private double price;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @TextIndexed    //全文检索字段，mongoDB数据库里需创建索引。mongoDB全文检索是按标点符号、空格等进行分词，要对中文进行全文检索最好用其它方式，如：es
    @ApiModelProperty(value = "详情")
    private String detail;
}
