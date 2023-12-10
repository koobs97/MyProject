package com.example.demo.batch.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.batch.dto.Batch000Dto;

@Mapper
public interface Batch000Mapper {
    public List<Batch000Dto> selectBfFinLedger(Map<String, Object> map);
}
