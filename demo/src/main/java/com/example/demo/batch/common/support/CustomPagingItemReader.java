package com.example.demo.batch.common.support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisPagingItemReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

public class CustomPagingItemReader<T> extends MyBatisPagingItemReader<T> {

    @Getter
    @Setter
    private SqlSessionTemplate sqlSessionTemplate;

    private CustomPagingItemReader<?> corePagingItemReader;

    @SuppressWarnings("hiding")
    public <T> T selectOne(String statement) {
        return sqlSessionTemplate.selectOne(statement);
    }

    @SuppressWarnings("hiding")
    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(statement, parameter);
    }

    public <E> List<E> selectList(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSessionTemplate.selectList(statement, parameter);
    }

    public CustomPagingItemReader<?> getReader(SqlSessionFactory sqlSessionFactory, SqlSessionTemplate sqlSessionTemplate) {
        this.corePagingItemReader = new CustomPagingItemReader<>();
        this.corePagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        this.corePagingItemReader.setSqlSessionTemplate(sqlSessionTemplate);

        return this.corePagingItemReader;
    }

    public Map<String, Object> setParameter(Object parameter) {
        ObjectMapper oMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> param = oMapper.convertValue(parameter, Map.class);

        return param;
    }
    
}
