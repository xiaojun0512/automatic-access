package com.cetc10.automaticaccess.dao;

import com.cetc10.automaticaccess.entity.Computer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoComputerDao extends MongoRepository<Computer,String> {
    List<Computer> getComputerListByName(String name);
}
