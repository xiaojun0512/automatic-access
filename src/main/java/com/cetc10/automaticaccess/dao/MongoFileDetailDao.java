package com.cetc10.automaticaccess.dao;

import com.cetc10.automaticaccess.entity.FileDetail;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoFileDetailDao extends MongoRepository<FileDetail,String> {

    FileDetail searchByMd5(String md5);
}
