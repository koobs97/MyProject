package com.example.demo.common.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * 다중 DB 연결 기능 구현 - mariadb
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value ="com.example.demo.batch.dao", sqlSessionFactoryRef = "mariadbSqlSessionFactory")
public class MariadbConfig {

    @Primary //Default로 사용하고 싶은 bean에 설정
    @Bean(name = "mariadbSqlSessionFactory")
    public SqlSessionFactory mariadbSessionFactory(@Qualifier("mariadbDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/mariadb/**/*.xml"));
        return sqlSessionFactoryBean.getObject();

    }

    @Primary
    @Bean(name = "mariadbSessionTemplate")
    public SqlSessionTemplate mariadbSessionTemplate(@Qualifier("mariadbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
