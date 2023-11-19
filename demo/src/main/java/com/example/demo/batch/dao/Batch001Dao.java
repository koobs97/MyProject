package com.example.demo.batch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.batch.vo.Batch001Vo;

@Mapper
public interface Batch001Dao {
    public List<Batch001Vo> findAll();
}
