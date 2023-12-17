package com.example.demo.batch.bean.practice;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.demo.batch.common.properties.BatchProperties;
import com.example.demo.batch.dto.practice.Batch003Dto;
import com.example.demo.batch.mapper.practice.Batch003Mapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class Batch003Bean {

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    BatchProperties properties  = new BatchProperties();
    String FILE_PATH = properties.getIN_FILE_PATH();
 
    /* Chunk 방식 예제 - FlatFileItemReader */
    @Bean(name = "FlatFileJob")
    public Job FlatFileJob(){
        return jobBuilderFactory
            .get("FlatFileJob")
            .start(Filestep())
            .incrementer(new RunIdIncrementer())
            .build();
    }


    @Bean(name = "Filestep")
    public Step Filestep(){
        return stepBuilderFactory
            .get("Filestep")
            .<Batch003Dto, Batch003Dto> chunk(5)
            .reader(FileItemReader())
            .writer(FileItemWriter())
            .build();
    }

    @Bean(name="FileItemReader")
    @StepScope
    public FlatFileItemReader<Batch003Dto> FileItemReader() {
        return new FlatFileItemReaderBuilder<Batch003Dto>()
                .name("FileItemReader")
                .resource(new FileSystemResource(FILE_PATH))
                .delimited()
                .delimiter(",")                       // 필요에 따라 구분자 설정
                .names("USER_ID","USER_NM","EMAIL")    // 열 이름 설정
                .fieldSetMapper(new Batch003Mapper())           // 파일에서 읽은 데이터를 vo에 저장할 매퍼 클래스
                .encoding("UTF-8")                     // 인코딩 설정
                .linesToSkip(1)                      // 스킵할 라인수 지정
                .build();
    }

    @Bean(name="FileItemWriter")
    @StepScope
    public MyBatisBatchItemWriter<Batch003Dto> FileItemWriter(){
    	MyBatisBatchItemWriter<Batch003Dto> writer = new MyBatisBatchItemWriter<>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("Batch003Mapper.insertAll");  //파일을 읽어서 sql로 db에 저장
    	return writer;
    }
}
/*
 * NOTE: 'FlatFileItemReader'
 * 
 * Spring Batch 프레임워크에서 제공하는 Reader 중 하나로 Flat File에서 데이터를 읽어오는데 사용된다. 
 * Flat File은 텍스트 형식으로 구성된 간단한 데이터 파일로 각 라인이 구분자로 구분된 레코드를 나타낸다. 
 */