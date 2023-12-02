package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


@SpringBootTest(classes = DemoApplication.class)
public class Batch001Test {

    private static final String JOB_NAME = "TaskletSampleJob";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    @Qualifier(JOB_NAME)
    private Job job;

    @DynamicPropertySource
    static void JobNnameProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.batch.job.name", () -> JOB_NAME );
    }

    @Test
    public void Btch001Test() throws Exception {

        jobRegistry.register(new ReferenceJobFactory(job));
        
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob(JOB_NAME), jobParameters);
    }
    
}
