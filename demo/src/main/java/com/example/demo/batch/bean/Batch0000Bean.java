package com.example.demo.batch.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.demo.batch.common.listener.JobCommonExecutionListener;
import com.example.demo.batch.common.listener.StepCommonExecutionListener;
import com.example.demo.batch.common.support.CoreBatchItemWriter;
import com.example.demo.batch.common.support.CorePagingItemReader;
import com.example.demo.batch.common.util.DateUtil;
import com.example.demo.batch.dto.Batch000Dto;

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
public class Batch0000Bean {
    
    /************************************************************************
     * Common Area
    ************************************************************************/

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("mariadbSqlSessionFactory")
    private SqlSessionFactory mariadbSqlSessionFactory;

    @Autowired
    @Qualifier("oracleSqlSessionFactory")
    private SqlSessionFactory oracleSqlSessionFactory;

    @Autowired
    @Qualifier("mariadbTransactionManager")
    private PlatformTransactionManager mariadbTransactionManager;

    @Autowired
    @Qualifier("oracleTransactionManager")
    private PlatformTransactionManager oracleTransactionManager;

    @Autowired
    @Qualifier("mariadbSessionTemplate")
    private SqlSessionTemplate mariadbSessionTemplate;

    @Autowired
    @Qualifier("oracleSessionTemplate")
    private SqlSessionTemplate oracleSessionTemplate;

    /* default PageSize를 1000으로 지정 */
    private final int PAGE_SIZE = 1000;








    /************************************************************************
     * Sample Job Template
    ************************************************************************/
    @Bean(name = "TemplateJob")
    public Job TemplateJob(){
        return jobBuilderFactory
            .get("TemplateJob")
            .listener(new JobCommonExecutionListener())     // 공통으로 사용할 JobExecutionListener
            .incrementer(new RunIdIncrementer())
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
                CorePagingItemReader<Batch000Dto> pagingReader = new CorePagingItemReader<>();
                pagingReader.setSqlSessionFactory(mariadbSqlSessionFactory);
                pagingReader.setSqlSessionTemplate(mariadbSessionTemplate);

                CoreBatchItemWriter<Batch000Dto> insertWriter = new CoreBatchItemWriter<>();
                insertWriter.setSqlSessionFactory(mariadbSqlSessionFactory);
                insertWriter.setSqlSessionTemplate(mariadbSessionTemplate);

                CoreBatchItemWriter<Batch000Dto> deletWriter = new CoreBatchItemWriter<>();
                deletWriter.setSqlSessionFactory(mariadbSqlSessionFactory);
                deletWriter.setSqlSessionTemplate(mariadbSessionTemplate);

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

                    Map<String, Object> Params = new HashMap<>();
                    Params.put("baseYm", "20231201");


                    /****************************
                     * @2. Parameter Setting
                     ****************************/
                    Batch000Dto pagingItem;
                    List<Batch000Dto> pagingItems = new ArrayList<>();

                    Map<String, Object> pagingParams = new HashMap<>();
                    pagingParams.put("baseYm", "20231201");
                    pagingReader.setParameterValues(pagingParams);

                    pagingReader.setQueryId("Batch000Mapper.selectBfFinLedger");
                    deletWriter.setStatementId("Batch000Mapper.deleteFinLedger");
                    insertWriter.setStatementId("Batch000Mapper.insertFinLedger");

                    pagingReader.open(chunckContext.getStepContext().getStepExecution().getExecutionContext());


                    /****************************
                     * @3. Logic Code
                     ****************************/
                    int i = 0;
                    while((pagingItem = pagingReader.read()) != null) {
                        if(pagingItem.getBaseYm().equals("202311")) {
                            pagingItem.setBaseYm("202312");
                        }

                        pagingItems.add(pagingItem);

                        if(i == 0) {
                            deletWriter.write(pagingItems);
                        }
                        i++;
                        
                    }

                    insertWriter.write(pagingItems);
                    pagingReader.close();




                    /****************************
                     * @4. Result Log
                     ****************************/
                    ExecutionContext stepContext = chunckContext.getStepContext().getStepExecution().getExecutionContext();
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");

                    String Message = "배치 결과" + "\n"
                                + "1. 초기화 " + decimalFormat.format(1200) + "건" + "\n"
                                + "2. 저장" + decimalFormat.format(2200) + "건" + "\n";

                    stepContext.put("MESSAGE", Message);

                    /* Commit */
                    mariadbTransactionManager.commit(status);

                } catch (Exception e) {

                    /* Rollback */
                    mariadbTransactionManager.rollback(status);
                    throw e;
                }

                return RepeatStatus.FINISHED;
            })
            .allowStartIfComplete(true)
            .build();
    }
}
