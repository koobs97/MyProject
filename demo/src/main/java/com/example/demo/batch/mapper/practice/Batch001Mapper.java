package com.example.demo.batch.mapper.practice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.batch.dto.practice.Batch001Dto;

@Mapper
public interface Batch001Mapper {
    public List<Batch001Dto> findAll();
}
