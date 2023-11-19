package com.example.demo.batch.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.example.demo.batch.vo.Batch001Vo;

@Repository
public class Batch001DaoImpl implements Batch001Dao{

    @Autowired
    @Qualifier("mariadbSessionTemplate")
    private SqlSession sqlSession;

    @Override
    public List<Batch001Vo> findAll() {

        List<Batch001Vo> resutList = sqlSession.selectList("findAll");

        return resutList;
    }

}
