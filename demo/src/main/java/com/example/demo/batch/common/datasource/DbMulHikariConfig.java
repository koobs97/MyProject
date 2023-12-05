package com.example.demo.batch.common.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * <h4>
 *  {@link HikariConfig} 다중 DB 연결 구현 <em> HikariCP </em>
 * </h4>
 * <ul>
 * <li> HikariCP(이하 Hikari Connection Pool)라는 자바 어플리케이션에서 사용되는 고성능 JDBC 커넥션 풀 라이브러리의 설정을 관리하기 위한 클래스
 * <li> HikariConfig를 사용하여 HikariCP의 설정을 정의하고, 이를 기반으로 {@link HikariDataSource}를 생성하여 JDBC 커넥션 풀을 관리한다. 
 * <li> HikariCP를 사용하면 커넥션 풀의 성능이 향상되고, 애플리케이션의 확장성이 향상된다. 
 * <li>primary 어노테이션을 사용하여 default bean으로 mariadb를 사용하도록 설정했다.
 * </ul>
 * 
 * @Brief   DbMulHikariConfig
 * @Author  Koo Bon Sang
 * @Date    2023.11.19
 * @Version 1.1.0
 * @See 
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
