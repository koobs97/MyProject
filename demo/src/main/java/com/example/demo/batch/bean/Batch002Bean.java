package com.example.demo.batch.bean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.batch.vo.Batch001Vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class Batch002Bean {

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    SqlSessionFactory sqlSessionFactory;
 
    /* Chunk 방식 예제 */
    @Bean(name = "ChunkSampleJob")
    public Job BatchJob(){
        return jobBuilderFactory
            .get("ChunkSampleJob")
            .start(step())
            .listener(jobExecutionListener())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    public Step step(){
        return stepBuilderFactory
            .get("step")
            .<Batch001Vo, Batch001Vo> chunk (2)
            .reader(mybatisReader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<Batch001Vo> mybatisReader(){

        MyBatisPagingItemReader<Batch001Vo> reader = new MyBatisPagingItemReader<>();
        reader.setPageSize(2);
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("Batch001Mapper.findAll");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Batch001Vo, Batch001Vo> processor() {
        return new ItemProcessor<Batch001Vo, Batch001Vo>() {
            @Override
            public Batch001Vo process (Batch001Vo model) throws Exception {
                model.setId("9999");
                model.setName("testname");

                return model;
            }
        };
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<Batch001Vo> writer(){
    	MyBatisBatchItemWriter<Batch001Vo> writer = new MyBatisBatchItemWriter<>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("Batch001Mapper.insertAll");
    	return writer;
    }

    public JobExecutionListener jobExecutionListener() {
        JobExecutionListener jobExecutionListener = new JobExecutionListener() {
            
            @Override
            public void beforeJob(JobExecution jobExecution) {
                // TODO wirte your before job code
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                // TODO wirte your after job code
            }
        };

        return jobExecutionListener;
    }

}
