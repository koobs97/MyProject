package com.example.demo.web.dao;

import java.util.List;

import com.example.demo.web.dto.UserData;

public interface UserDao {
    public List<UserData> findAll();
}
