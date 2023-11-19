package com.example.demo.common.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * 다중 DB 연결 기능을 구현하기 위해서는 HikariCP를 사용해야 한다. 
 */
@Configuration
public class MulDatasourceConfig {
    
    @Bean
    @Primary
    @Qualifier("mariadbHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.mariadb")
    public HikariConfig mariadbHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    @Qualifier("mariadbDataSource")
    public DataSource mariadbDataSource() throws Exception {
        DataSource dataSource = new HikariDataSource(mariadbHikariConfig());
        return dataSource;
    }

    @Bean
    @Qualifier("oracleHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.oracle")
    public HikariConfig oracleHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Qualifier("oracleDataSource")
    public DataSource oracleDataSource() throws Exception {
        DataSource dataSource = new HikariDataSource(oracleHikariConfig());
        return dataSource;
    }
}
