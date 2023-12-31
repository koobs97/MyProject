package com.example.demo.batch.bean.practice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.demo.batch.common.listener.JobCommonExecutionListener;
import com.example.demo.batch.common.listener.StepCommonExecutionListener;
import com.example.demo.batch.common.support.CustomBatchItemWriter;
import com.example.demo.batch.common.support.CustomPagingItemReader;
import com.example.demo.batch.common.util.DateUtil;
import com.example.demo.batch.dto.practice.Batch000Dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h4>
 *  KAKAO BANK 배치 프레임워크 샘플 구현 예제소스 연습
 * </h4>
 * 
 * 샘플 Job에 여러개의 기능을 구현하여 복사 붙여넣기 하여 활용할 수 있도록 함
 * 
 * <ul>
 * <li> Tasklet 방식 샘플
 * <li> Chunk 방식 샘플
 * <li> FILE To DB 샘플
 * <li> Tasklet의 명시적인 commit / rollback
 * </ul> 
 * 
 * @Brief   Job, Step
 * @Author  Koo Bon Sang
 * @Date    2023.12.07
 * @Version 1.1.0
 * @See 
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class Batch000Bean {
    
    /************************************************************************
     * Common Area
    ************************************************************************/

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("mariadbTransactionManager")
    private PlatformTransactionManager mariadbTransactionManager;

    @Autowired
    @Qualifier("mariadbSessionTemplate")
    private SqlSessionTemplate mariadbSessionTemplate;





    /************************************************************************
     * Sample Job Template
    ************************************************************************/
    @Bean(name = "TemplateJob")
    public Job TemplateJob(){
        return jobBuilderFactory
            .get("TemplateJob")
            .listener(new JobCommonExecutionListener())     // 공통으로 사용할 JobExecutionListener
            .incrementer(new RunIdIncrementer())            // 
            .start(StepTest1())
            .build();
    }

    @Bean(name = "StepTest1")
    public Step StepTest1(){
        return stepBuilderFactory
            .get("StepTest1")
            .listener(new StepCommonExecutionListener())
            .tasklet((contribution, chunckContext) -> {
                
                /* getTransaction */
                TransactionStatus status = mariadbTransactionManager.getTransaction(new DefaultTransactionDefinition());

                /* Paging Reader 객체 선언 */
                CustomPagingItemReader<Batch000Dto> reader = new CustomPagingItemReader<>();
                reader.setSqlSessionTemplate(mariadbSessionTemplate);

                CustomBatchItemWriter<Batch000Dto> writer = new CustomBatchItemWriter<>();
                writer.setSqlSessionTemplate(mariadbSessionTemplate);


                try {
                    /************************************************************************
                     * MyBatisPagingItemReader를 이용한 read, write 예제
                     ************************************************************************/

                    /* 배치 실행 시 할당된 Job Parameter를 받아온다. */
                    JobParameters jobParameters = chunckContext.getStepContext().getStepExecution().getJobParameters();

                    /****************************
                     * @1. Parameter Validation
                     ****************************/
                    if(jobParameters.getString("baseYm") != null) {
                        if(!DateUtil.isValidDate(jobParameters.getString("baseYm"), "yyyyMM")) {
                            log.error("파라미터가 부적합합니다. ");
                            throw new Exception();
                        }
                    }


                    /****************************
                     * @2. Parameter Setting
                     ****************************/
                    Map<String, Object> Params = new HashMap<>();
                    Params.put("baseYm", "202312");


                    /****************************
                     * @3. Logic Code
                     ****************************/

                    List<Batch000Dto> result =  reader.selectList("Batch000Mapper.selectBfFinLedger", Params);


                    if(result.size() >  0) {
                        writer.delete("Batch000Mapper.deleteFinLedger", Params);
                    }

                    for (Batch000Dto dto : result) {
                        dto.setBaseYm(jobParameters.getString("baseYm"));

                        writer.insert("Batch000Mapper.insertFinLedger", dto);
                    }
                    log.info("이월 작업 완료");
                    

                    /* Commit */
                    mariadbTransactionManager.commit(status);

                } catch (Exception e) {

                    /* Rollback */
                    mariadbTransactionManager.rollback(status);
                    throw e;
                }

                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
