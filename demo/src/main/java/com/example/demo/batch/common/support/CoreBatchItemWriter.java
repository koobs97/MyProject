package com.example.demo.batch.common.support;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CoreBatchItemWriter<T> extends MyBatisBatchItemWriter<T> {

    @Getter
    @Setter
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(String statement) {
        return this.performChecker("INSERT", statement, (Object)null);
    }

    public int insert(String statement, Object parameter) {
        return this.performChecker("INSERT", statement, parameter);
    }

    public int update(String statement) {
        return this.performChecker("UPDATE", statement, (Object)null);
    }

    public int update(String statement, Object parameter) {
        return this.performChecker("UPDATE", statement, parameter);
    }

    public int delete(String statement) {
        return this.performChecker("DELETE", statement, (Object)null);
    }

    public int delete(String statement, Object parameter) {
        return this.performChecker("DELETE", statement, parameter);
    }
    

    private int performChecker(String queryType, String statement, Object parameter) {
        int result = 0;
        if("INSERT".equals(queryType)){
            if (parameter != null) {
                result = this.sqlSessionTemplate.insert(statement, parameter);
            } else {
                result = this.sqlSessionTemplate.insert(statement);
            }
        } else if ("UPDATE".equals(queryType)) {
            if (parameter != null) {
                result = this.sqlSessionTemplate.update(statement, parameter);
            } else {
                result = this.sqlSessionTemplate.update(statement);
            }
        } else if ("DELETE".equals(queryType)) {
            if(parameter != null) {
                result = this.sqlSessionTemplate.delete(statement, parameter);
            } else {
                result = this.sqlSessionTemplate.delete(statement);
            }
        }

        return result;
    }

    private String deleteStatementId;

    public void setDeleteStatementId(String deleteStatementId) {
        this.deleteStatementId = deleteStatementId;
    }

    @Override
    public void setStatementId(String statementId) {
        super.setStatementId(statementId);
    }

    public void doWrite(List<? extends T> items) {
        // 삽입 작업을 수행하는 코드

        // ...

        // 삭제 작업을 수행하는 코드
        if (deleteStatementId != null) {
            setStatementId(deleteStatementId);
            super.write(items);
        }
    }
    
}
