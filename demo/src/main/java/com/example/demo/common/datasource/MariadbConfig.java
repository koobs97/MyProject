package com.example.demo.common.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
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
public class MariadbConfig {

    @Bean(name = "mariadbDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.mariadb-datasource")
    public DataSource mariadbSource () {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mariadbSqlSessionFactory")
    public SqlSessionFactory mariadbSessionFactory(@Qualifier("mariadbDataSource") DataSource dataSource, ApplicationContext ApplicationContext) throws Exception {
        
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/mariadb/**/*.xml"));
        return sqlSessionFactoryBean.getObject();

    }

    @Bean(name = "mariadbSessionTemplate")
    public SqlSessionTemplate mariadbSessionTemplate(@Qualifier("mariadbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
