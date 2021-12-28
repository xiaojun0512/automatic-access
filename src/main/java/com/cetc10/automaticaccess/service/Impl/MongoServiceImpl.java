package com.cetc10.automaticaccess.service.Impl;

import com.cetc10.automaticaccess.dao.MongoComputerDao;
import com.cetc10.automaticaccess.dao.MongoUserDao;
import com.cetc10.automaticaccess.entity.Computer;
import com.cetc10.automaticaccess.entity.User;
import com.cetc10.automaticaccess.service.MongoService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MongoServiceImpl implements MongoService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private MongoUserDao mongoUserDao;
    @Autowired
    private MongoComputerDao mongoComputerDao;

    @Override
    public void addUser(User user) {
        mongoTemplate.insert(user); //方式1
//        mongoUserDao.insert(user);    //方式2
    }

    @Override
    public List<User> getUserListByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query,User.class);   //方式1
//        return mongoUserDao.getUserListByName(name);   //方式2
    }

    @Override
    public User getUserListById(String id) {
        return mongoTemplate.findById(id,User.class);   //方式1
//        return mongoUserDao.findById(id).get();   //方式2
    }

    @Override
    public void addUser(Computer computer) {
        if (computer.getCreateTime() == null) {
            computer.setCreateTime(new Date());
        }
        mongoComputerDao.save(computer);
    }

    @Override
    public List<Computer> getComputerListByName(String name) {
        return mongoComputerDao.getComputerListByName(name);
    }

    @Override
    public Computer getComputerListById(String id) {
        return mongoComputerDao.findById(id).get();
    }

    @Override
    public String saveFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), filename, file.getContentType());
            return objectId.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GridFSFile searchFileById(Object id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return gridFsTemplate.findOne(query);
    }
}
