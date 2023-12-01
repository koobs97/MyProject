package com.example.demo.batch.bean;

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

import com.example.demo.batch.vo.Batch002Vo;

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
            .<Batch002Vo, Batch002Vo> chunk (2)
            .reader(mybatisReader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<Batch002Vo> mybatisReader(){

        MyBatisPagingItemReader<Batch002Vo> reader = new MyBatisPagingItemReader<>();
        reader.setPageSize(2);
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("Batch001Mapper.findAll");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Batch002Vo, Batch002Vo> processor() {
        return new ItemProcessor<Batch002Vo, Batch002Vo>() {
            @Override
            public Batch002Vo process (Batch002Vo vo) throws Exception {
                vo.setId("9999");
                vo.setName("testname");

                return vo;
            }
        };
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<Batch002Vo> writer(){
    	MyBatisBatchItemWriter<Batch002Vo> writer = new MyBatisBatchItemWriter<>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("Batch001Mapper.insertAll");
    	return writer;
    }
}
