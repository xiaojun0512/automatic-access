package com.cetc10.automaticaccess.dao;

import com.cetc10.automaticaccess.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoUserDao extends MongoRepository<User,String> {

    List<User> getUserListByName(String name);
}
