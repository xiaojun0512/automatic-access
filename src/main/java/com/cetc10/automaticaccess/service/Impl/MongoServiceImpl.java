package com.cetc10.automaticaccess.service.Impl;

import com.cetc10.automaticaccess.dao.MongoComputerDao;
import com.cetc10.automaticaccess.dao.MongoFileDetailDao;
import com.cetc10.automaticaccess.dao.MongoUserDao;
import com.cetc10.automaticaccess.entity.Computer;
import com.cetc10.automaticaccess.entity.FileDetail;
import com.cetc10.automaticaccess.entity.User;
import com.cetc10.automaticaccess.service.MongoService;
import com.cetc10.automaticaccess.util.FileUtils;
import com.cetc10.automaticaccess.util.ResultUtils;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private MongoFileDetailDao mongoFileDetailDao;

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
    public ResultUtils saveFile(MultipartFile file) {
        FileDetail fileDetail = new FileDetail();
        String filename = file.getOriginalFilename();
        try {
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            FileDetail searchByMd5file = mongoFileDetailDao.searchByMd5(md5);
            if (searchByMd5file != null) {
                log.error("文件重复上传");
                return ResultUtils.error("文件重复上传");
            }

            fileDetail.setMd5(md5);
            fileDetail.setId(UUID.randomUUID().toString());
            fileDetail.setCreateTime(new Date());
            fileDetail.setFileName(filename);
            String type = filename.substring(filename.lastIndexOf(".") + 1);
            fileDetail.setType(type);
            if ("txt".equals(type)) {
                fileDetail.setContent(FileUtils.readTxtToString(file.getInputStream()));
            } else if ("doc".equals(type) || "docx".equals(type)) {
                fileDetail.setContent(FileUtils.readWordToString(file.getInputStream()));
            }
            fileDetail.setSize(file.getSize());

            //保存文件到mongodb并返回文件id
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), filename, file.getContentType());
            fileDetail.setFileId(objectId.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success(mongoFileDetailDao.save(fileDetail));
    }

    @Override
    public GridFSFile searchFileById(Object id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return gridFsTemplate.findOne(query);
    }

    @Override
    public List<Computer> getComputerDetail(String word) {
        Query query = new Query();
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(word);
        query.addCriteria(textCriteria);
        return mongoTemplate.find(query,Computer.class);
    }
}
