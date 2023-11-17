package com.example.demo.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.batch.dao.Batch001Dao;
import com.example.demo.batch.vo.Batch001Vo;

@Service
public class Batch001ServiceImpl implements Batch001Service{
    
    @Autowired Batch001Dao Batch001Dao;

    public List<Batch001Vo> findAll() {

        List<Batch001Vo> resultList = Batch001Dao.findAll();

        return resultList;
    }

}
