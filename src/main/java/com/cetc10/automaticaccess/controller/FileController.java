package com.cetc10.automaticaccess.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
@Api(tags = "文件接入")
public class FileController {

    @GetMapping("hello")
    @ApiOperation(value = "输出文本")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "word",value = "文本",required = true)
    })
    public String hello(String word){
        return word;
    }
}
