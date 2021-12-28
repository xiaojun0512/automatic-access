package com.cetc10.automaticaccess.service;

import com.cetc10.automaticaccess.entity.Computer;
import com.cetc10.automaticaccess.entity.User;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MongoService {
    void addUser(User user);

    List<User> getUserListByName(String name);

    User getUserListById(String id);

    void addUser(Computer computer);

    List<Computer> getComputerListByName(String name);

    Computer getComputerListById(String id);

    String saveFile(MultipartFile multipartFile);

    GridFSFile searchFileById(Object id);
}
