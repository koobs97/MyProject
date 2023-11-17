package com.example.demo.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.web.dto.UserData;

@Mapper
public interface UserMapper {
    
    @Select("select * from TB_USER")
    public List<UserData> findAll();
}
