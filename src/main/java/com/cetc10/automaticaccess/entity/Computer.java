package com.cetc10.automaticaccess.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
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
}
