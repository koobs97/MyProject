package com.example.demo.batch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.batch.vo.Batch001Vo;

@Mapper
public interface Batch001Mapper {

    public List<Batch001Vo> findAll();
}
