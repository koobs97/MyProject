package com.example.demo.web.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.web.dto.UserData;
import com.example.demo.web.mapper.UserMapper;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired UserMapper uMapper;

    @Override
    public List<UserData> findAll() {
        
        return uMapper.findAll();
    }
    
}
