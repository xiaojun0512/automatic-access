package com.cetc10.automaticaccess.controller;

import com.cetc10.automaticaccess.entity.Computer;
import com.cetc10.automaticaccess.entity.FileDetail;
import com.cetc10.automaticaccess.entity.User;
import com.cetc10.automaticaccess.service.MongoService;
import com.cetc10.automaticaccess.util.ResultUtils;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("mongodb")
@Api(tags = "mongodb数据接口")
public class MongoController {
    @Autowired
    private MongoService mongoService;

    @PostMapping("addUser")
    @ApiOperation(value = "新增用户")
    public void addUser(User user){
        mongoService.addUser(user);
    }

    @GetMapping("getUserListByName")
    @ApiOperation(value = "根据名字获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "name",value = "名字",required = true)
    })
    public List<User> getUserList(String name){
        return mongoService.getUserListByName(name);
    }

    @GetMapping("getUserListById")
    @ApiOperation(value = "根据ID获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "id",value = "id",required = true)
    })
    public User getUserListById(String id){
        return mongoService.getUserListById(id);
    }

    @PostMapping("addComputer")
    @ApiOperation(value = "新增电脑")
    public void addComputer(Computer computer){
        mongoService.addUser(computer);
    }

    @GetMapping("getComputerListByName")
    @ApiOperation(value = "根据名称获取电脑")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "name",value = "名称",required = true)
    })
    public List<Computer> getComputerListByName(String name){
        return mongoService.getComputerListByName(name);
    }

    @GetMapping("getComputerById")
    @ApiOperation(value = "根据ID获取电脑")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "id",value = "id",required = true)
    })
    public Computer getComputerById(String id){
        return mongoService.getComputerById(id);
    }

    @PostMapping("saveFile")
    @ApiOperation(value = "保存文件")
    public ResultUtils saveFile(MultipartFile file){
        return mongoService.saveFile(file);
    }

    @GetMapping("downLoadFile")
    @ApiOperation(value = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "id",value = "文件详情id",required = true)
    })
    public ResultUtils downLoadFile(String id){
        return mongoService.downLoadFile(id);
    }

    @PostMapping("searchFileById")
    @ApiOperation(value = "根据文件id获取文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "objectId",value = "文件id",required = true)
    })
    public GridFSFile searchFileById(String objectId){
        return mongoService.searchFileById(objectId);
    }

    @PostMapping("getComputerDetail")
    @ApiOperation(value = "全文检索-搜索电脑详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "word",value = "搜索词",required = true)
    })
    public List<Computer> getComputerDetail(String word){
        return mongoService.getComputerDetail(word);
    }
}
