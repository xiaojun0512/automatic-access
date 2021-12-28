package com.cetc10.automaticaccess.controller;

import com.cetc10.automaticaccess.entity.Computer;
import com.cetc10.automaticaccess.entity.User;
import com.cetc10.automaticaccess.service.MongoService;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("getComputerListById")
    @ApiOperation(value = "根据ID获取电脑")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "id",value = "id",required = true)
    })
    public Computer getComputerListById(String id){
        return mongoService.getComputerListById(id);
    }

    @PostMapping("saveFile")
    @ApiOperation(value = "保存文件")
    public String saveFile(MultipartFile file){
        return mongoService.saveFile(file);
    }

    @PostMapping("searchFileById")
    @ApiOperation(value = "根据文件id获取文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "string",name = "id",value = "id",required = true)
    })
    public GridFSFile searchFileById(String id){
        return mongoService.searchFileById(id);
    }
}