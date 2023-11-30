package com.example.demo.common.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @brief   Configuration
 * @details 다중 DB 연결 구현(HikariCP)
 * @author  Koo Bon Sang
 * @date    2023.11.19
 * @version 1.1.0
 * 
 * @Note 다중 DB 연결 기능을 구현하기 위해서는 HikariCP를 사용해야 한다. 
 * primary 어노테이션을 사용하여 default bean으로 mariadb를 사용하도록 설정했다. 
 * HikariConfig는 자바 기반의 경량 커넥션 풀 라이브러리를 사용할 때 쓰는 설정 클래스이다. 
 * 이 클래스를 통해 데이터베이스 연결 풀의 속성들을 설정할 수 있다. 
 * 
 * == Modification Information ==
 * 
 * DATA            AUTHOR          NOTE
 * ------------    ------------    ------------
 * 2023.11.19      Koo Bon Sang    First Created
 * 
 */
@Configuration
public class DbMulHikariConfig {
    
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
