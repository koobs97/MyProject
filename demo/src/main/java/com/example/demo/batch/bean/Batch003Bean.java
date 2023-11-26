package com.example.demo.batch.bean;

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
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.demo.batch.mapper.Batch003Mapper;
import com.example.demo.batch.vo.Batch003Vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class Batch003Bean {

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    SqlSessionFactory sqlSessionFactory;
 
    /* Chunk 방식 예제 - FlatFileItemReader */
    @Bean(name = "flatFileJob")
    public Job BatchJob(){
        return jobBuilderFactory
            .get("flatFileJob")
            .start(filestep())
            .incrementer(new RunIdIncrementer())
            .build();
    }


    public Step filestep(){
        return stepBuilderFactory
            .get("filestep")
            .<Batch003Vo, Batch003Vo> chunk(5)
            .reader(fileItemReader())
            .writer(fileItemWriter())
            .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Batch003Vo> fileItemReader() {
        return new FlatFileItemReaderBuilder<Batch003Vo>()
                .name("fileItemReader")
                .lineTokenizer(new DelimitedLineTokenizer())
                /*
                 * NOTE: <DelimitedLineTokenizer>
                 * Spring Batch에서 사용되는 분할 전략 중 하나로 텍스트 기반의 파일에서 한 라이능ㄹ 필드로 분할하는데 사용된다. 
                 */
                .linesToSkip(1)             // 첫째 라인은 스킵
                .fieldSetMapper(new Batch003Mapper())   // 파일에서 읽은 데이터를 vo에 저장할 클래스
                .resource(new FileSystemResource("C:\\VSCode\\File\\sample-list.csv"))  // 읽을 파일
                .strict(false)
                .build();
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<Batch003Vo> fileItemWriter(){
    	MyBatisBatchItemWriter<Batch003Vo> writer = new MyBatisBatchItemWriter<>();
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