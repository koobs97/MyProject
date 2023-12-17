package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


@SpringBootTest(classes = DemoApplication.class)
public class Batch000Test {

    /*******************************************************************
     * 공통
     *******************************************************************/

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @DynamicPropertySource
    static void BatchEnableProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.batch.job.enabled", () -> "false");
    }

    /* 유니크 파라미터 생성 */
    protected JobParametersBuilder getJobParametersBuilder() {
        return new JobParametersBuilder(new JobLauncherTestUtils().getUniqueJobParameters());
    }





    /*******************************************************************
     * JobCommonExecutionTestjob 테스트
     *******************************************************************/
    
    @Autowired
    @Qualifier("JobCommonExecutionTestjob")
    private Job JobCommonExecutionTestjob;

    @DynamicPropertySource
    static void JobNnameProperties2(DynamicPropertyRegistry registry) {
        registry.add("spring.batch.job.names", () -> "JobCommonExecutionTestjob" );
    }

    @Test
    public void Btch000Test() throws Exception {

        jobRegistry.register(new ReferenceJobFactory(JobCommonExecutionTestjob));
        
        JobParameters jobParameters = getJobParametersBuilder()
                .addString("UserId", "Koo Bon Sang")
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("JobCommonExecutionTestjob"), jobParameters);
    }




    /*******************************************************************
     * TemplateJob 테스트
     *******************************************************************/
    @Autowired
    @Qualifier("TemplateJob")
    private Job TemplateJob;
    
    @DynamicPropertySource
    static void JobNnameProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.batch.job.names", () -> "TemplateJob" );
    }

    @Test
    public void Btch004Test() throws Exception {

        jobRegistry.register(new ReferenceJobFactory(TemplateJob));
        
        JobParameters jobParameters = getJobParametersBuilder()
                .addString("UserId", "Koo Bon Sang")
                .addString("baseYm", "202312")
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("TemplateJob"), jobParameters);
    }
    
}
