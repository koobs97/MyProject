package com.example.demo.batch.mapper.practice;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Batch002Mapper {
    public int insertAll();
}
