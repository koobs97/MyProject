package com.example.demo.batch.bean;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.batch.service.Batch001Service;
import com.example.demo.batch.vo.Batch001Vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class Batch001Bean {
    
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired Batch001Service batch001Service;

    /* Tasklet 방식 예제 */
    @Bean(name = "TaskletSampleJob")
    public Job BatchJob(){
        return jobBuilderFactory
            .get("TaskletSampleJob")
            .listener(jobExecutionListener())
            .start(step1())
            .next(step2())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    public Step step1(){
        return stepBuilderFactory
            .get("step1")
            .tasklet((contribution, chunckContext) -> {
                
                // TODO write your step code

                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true)
            .build();
    }

    public Step step2(){
        return stepBuilderFactory
            .get("step2")
            .tasklet((contribution, chunckContext) -> {
                
                // TODO write your step code

                List<Batch001Vo> result = batch001Service.findAll();

                for(int i = 0; i < result.size(); i++) {
                    System.out.println("id : " + result.get(i).getId() + "  name : " + result.get(i).getName());
                }

                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true)
            .build();
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
