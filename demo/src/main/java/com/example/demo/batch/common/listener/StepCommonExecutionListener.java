package com.example.demo.batch.common.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepCommonExecutionListener implements StepExecutionListener {

    private String MESSAGE;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        MESSAGE = "DEFAULT MESSAGE";

        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("MESSAGE", MESSAGE);


        log.info("=========================================");
        log.info("Common StepExecutionListener : beforeStep");
        log.info("=========================================");
        log.info("Step Name : "      + stepExecution.getStepName()                                      );
        log.info("Job Name : "       + stepExecution.getJobExecution().getJobInstance().getJobName()    );
        log.info("Job Parameters : " + stepExecution.getJobExecution().getJobParameters()               );
        log.info("=======================================");
    }

    @Override
    @Nullable
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("========================================");
        log.info("Common StepExecutionListener : afterStep");
        log.info("========================================");
        log.info("Job Name : "       + stepExecution.getJobExecution().getJobInstance().getJobName()  );
        log.info("Job Parameters : " + stepExecution.getJobExecution().getJobParameters()             );

        return ExitStatus.COMPLETED;
    }
    
}
