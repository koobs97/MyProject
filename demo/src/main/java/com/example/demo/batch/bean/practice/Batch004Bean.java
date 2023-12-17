package com.example.demo.batch.bean.practice;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.batch.common.listener.JobCommonExecutionListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Batch004Bean {

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    
    @Bean(name = "JobCommonExecutionTestjob")
    public Job JobCommonExecutionTestjob(){
        return jobBuilderFactory
            .get("JobCommonExecutionTestjob")
            .listener(new JobCommonExecutionListener())     // Common Execution : Job 실행 시 공통으로 사용할 listener
            .start(ExecutionTeststep())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean("ExecutionTeststep")
    public Step ExecutionTeststep(){
        return stepBuilderFactory
            .get("ExecutionTeststep")
            .tasklet((contribution, chunckContext) -> {
                
                log.info("ExecutionTeststep");

                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true)
            .build();
    }
}
