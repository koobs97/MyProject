package com.example.demo.batch.bean;

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

import com.example.demo.batch.common.listener.JobCommonExecutionListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h4>
 *  <em> Tasklet </em> 방식의 Job 예제 구현
 * </h4>
 * <ul>
 * <li> 한 개의 Job, 3개의 Step으로 이루어진 배치 예제 작성. {@link Job} 및 {@link Step}이 흘러가는 간단한 샘플코드
 * <li> {@link JobExecutionListener}를 이용한 beforeJob, afterJob을 구현할 수 있도록 하는 샘플코드
 * <li> 배치 설정파일로 지정하기 위해 {@code @RequiredArgsConstructor}, {@code @Configuration} 어노테이션을 사용해야 한다. 
 * </ul>
 * 
 * @Brief   Job, Step
 * @Author  Koo Bon Sang
 * @Date    2023.11.23
 * @Version 1.1.0
 * @See com.example.demo.Batch001Test
 * 
 */

 @Slf4j
@RequiredArgsConstructor
@Configuration
public class Batch001Bean {
    
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    /**
     * TaskletS 방식의 Job
     * 
     * <ul>
     * <li> {@link JobBuilderFactory} 클래스를 통해 TaskletSampleJob의 이름을 가지는 Job의 Bean을 생성해주었다.
     * <li> {@link JobExecutionListener#listener} 통해 beforeJob 처리와 afterJob 처리를 할 수 있다.
     * <li> start()와 next()를 통해 다음에 실행할 step을 호출한다. 
     * <li> {@link RunIdIncrementer} 클래스를 통해 Job을 실행할 때마다 JobParameters의 nextRunId 값을 증가시켜 새로운 인스턴스를 생성한다.
     * </ul>
     * 
     * @Type Job
     * @Bean name : TaskletSampleJob
     */
    @Bean(name = "TaskletSampleJob")
    public Job TaskletSampleJob(){
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

                log.info("=================");
                log.info("step1 is executed");
                log.info("=================");

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

                log.info("=================");
                log.info("step2 is executed");
                log.info("=================");

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
