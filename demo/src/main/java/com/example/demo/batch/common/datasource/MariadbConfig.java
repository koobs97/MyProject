package com.example.demo.batch.common.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * 다중 DB 연결 기능 구현 - mariadb
 */
@Configuration
@EnableTransactionManagement
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

    @Bean(name = "mariadbTransactionManager")
    public PlatformTransactionManager mariadbTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}

/*
 * NOTE: SqlSessionTemplate
 * Mybatis에서 제공하는 클래스로 'SqlSession'을 대신하여 데이터베이스와의 상호작용을 한다. 
 * 어노테이션은 별도로 제공되지 않고 별도의 config 클래스 파일을 두어 빈으로 등록하고 관리한다. 
 * 
 * @Primary 어노테이션을 사용하여 mariadb에 대한 bean을 우선순위로 주입하기 위해 사용했다. 
 * @Qualifier 어노테이션을 사용하면 oracle에 대한 bean을 주입하여 사용할 수 있다. 
 * 
 * 우선순위 : @Primary < @Qualifier
 */