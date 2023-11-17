package com.example.demo.batch.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.batch.mapper.Batch001Mapper;
import com.example.demo.batch.vo.Batch001Vo;

@Repository
public class Batch001DaoImpl implements Batch001Dao{
    
    @Autowired Batch001Mapper batch001Mapper;

    @Override
    public List<Batch001Vo> findAll() {

        List<Batch001Vo> resutList = batch001Mapper.findAll();

        return resutList;
    }

}
