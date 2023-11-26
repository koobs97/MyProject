package com.example.demo.batch.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.example.demo.batch.vo.Batch003Vo;

@Repository
public class Batch003DaoImpl implements Batch003Dao{

    @Autowired
    @Qualifier("mariadbSessionTemplate")
    private SqlSession mariadbSqlSession;

    @Autowired
    @Qualifier("oracleSessionTemplate")
    private SqlSession oracleSqlSession;

    @Override
    public int insertAll(Batch003Vo vo) {
        return mariadbSqlSession.insert("Batch003Mapper.insertAll", vo);
    }
}
