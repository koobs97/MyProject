package com.example.demo.batch.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.batch.vo.Batch003Vo;

@Mapper
public interface Batch003Dao {
    public int insertAll(Batch003Vo vo);
}
