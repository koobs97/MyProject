package com.example.demo.batch.common.listener;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobCommonExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /*
         * 배치 작업 전 수행해야 할 리스트
         * 
         * 1. 전처리 작업 수행
         * 2. 파라미터 유효성 검사
         * 3. 환경 설정 확인
         * 4. 의존 관계 확인
         * 5. 작업 시작 로그
         * 6. 권한 및 보안 체크
         */

         /* 2.파라미터 유효성 검사 예시 */
        if(jobExecution.getJobParameters().getString("baseYm") == null) {
            throw new IllegalArgumentException("Input baseYm parameter is required.");
        }
        
        log.info("=======================================");
        log.info("Common JobExecutionListener : beforeJob");
        log.info("=======================================");
        log.info("Job Name : "       + jobExecution.getJobInstance().getJobName()    );
        log.info("Job Parameters : " + jobExecution.getJobParameters()               );
        log.info("Job Start Time : " + Dateformat.format(jobExecution.getStartTime()));
        log.info("=======================================");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("=======================================");
        log.info("Common JobExecutionListener : afterJob");
        log.info("=======================================");
        log.info("Job Name : "       + jobExecution.getJobInstance().getJobName()  );
        log.info("Job Parameters : " + jobExecution.getJobParameters()             );
        log.info("Job Result : "     + jobExecution.getExitStatus().getExitCode()  );

        if(jobExecution.getExitStatus().getExitCode().equals("FAILED")) {
            log.error("Job Failed!!");
            log.error("Error Message : " + jobExecution.getExitStatus().getExitDescription());
        }

        log.info("Job End Time : "   + Dateformat.format(jobExecution.getEndTime()));
        log.info("=======================================");
    }
    
}
