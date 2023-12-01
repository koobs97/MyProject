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
