package com.example.demo.batch.bean.practice;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.batch.dto.practice.Batch002Dto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class Batch002Bean {

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    SqlSessionFactory sqlSessionFactory;
 
    /* Chunk 방식 예제 - MybatisItemReader */
    @Bean(name = "ChunkSampleJob")
    public Job ChunkSampleJob(){
        return jobBuilderFactory
            .get("ChunkSampleJob")
            .start(step())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    public Step step(){
        return stepBuilderFactory
            .get("step")
            .<Batch002Dto, Batch002Dto> chunk (2)
            .reader(mybatisReader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    /**
     * {@code MyBatisPagingItemReader} 예제
     * 
     * <ul>
     * <li> 대량의 데이터를 페이징하여 처리할 수 있다.
     * </ul>
     * 
     * @Type Step
     * @Bean
     */
    @Bean
    @StepScope
    public MyBatisPagingItemReader<Batch002Dto> mybatisReader(){

        MyBatisPagingItemReader<Batch002Dto> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);         // MyBatis SqlSessionFactory 설정
        reader.setPageSize(2);                         // 한번에 읽어올 데이터의 양 설정
        reader.setQueryId("Batch001Mapper.findAll");    // MyBatis 매퍼 XML에서 정의한 쿼리 ID

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Batch002Dto, Batch002Dto> processor() {
        return new ItemProcessor<Batch002Dto, Batch002Dto>() {
            @Override
            public Batch002Dto process (Batch002Dto vo) throws Exception {
                vo.setId("9999");
                vo.setName("testname");

                return vo;
            }
        };
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<Batch002Dto> writer(){
    	MyBatisBatchItemWriter<Batch002Dto> writer = new MyBatisBatchItemWriter<>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("Batch001Mapper.insertAll");
    	return writer;
    }
}
