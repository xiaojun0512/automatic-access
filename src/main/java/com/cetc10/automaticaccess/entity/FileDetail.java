package com.cetc10.automaticaccess.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "fileDetail")
@ApiModel(value = "文件详情")
public class FileDetail {
    @Id
    private String id;
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "文件类型")
    private String type;
    @ApiModelProperty(value = "文件大小")
    private long size;
    @ApiModelProperty(value = "上传时间")
    private Date createTime;
    @ApiModelProperty(value = "文件内容")
    private String content;
    @ApiModelProperty(value = "文件md5")
    private String md5;
    @ApiModelProperty(value = "文件id")
    private ObjectId objectId;

}
