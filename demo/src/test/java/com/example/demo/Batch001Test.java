package com.example.demo;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
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

    private static Map<String, Object> createDynamicParameters() {
        
        // 동적으로 추가할 파라미터를 Map으로 생성
        Map<String, Object> dynamicParameters = Map.of(
                "currentTime", System.currentTimeMillis(),
                "parameter2", 42,
                "UserId", "koo Bon Sang"
        );

        return dynamicParameters;
    }

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

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        Map<String, Object> dynamicParameters = createDynamicParameters();

        // Map의 키와 값을 JobParametersBuilder에 추가
        for (Map.Entry<String, Object> entry : dynamicParameters.entrySet()) {
            addParameter(jobParametersBuilder, entry.getKey(), entry.getValue());
        }

        // 최종적인 JobParameters 생성
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        jobLauncher.run(jobRegistry.getJob(JOB_NAME), jobParameters);
    }
    
    /**
     * JobParameter를 동적으로 추가 세팅하기 위한 제네릭메소드
     * 
     * @param <T>
     * @param builder
     * @param key
     * @param value
     */
    private static <T> void addParameter(JobParametersBuilder builder, String key, T value) {
        if (value instanceof String) {
            builder.addString(key, (String) value);
        } else if (value instanceof Long) {
            builder.addLong(key, (Long) value);
        } else if (value instanceof Integer) {
            Integer value2 = (Integer) value;
            builder.addLong(key, value2.longValue());
        } else if (value instanceof Date) {
            builder.addDate(key, (Date) value);
        } else if (value instanceof Double) {
            builder.addDouble(key, (Double) value);
        } else if (value instanceof JobParameters) {
            builder.addJobParameters((JobParameters) value);
        } else if (value instanceof JobParameter) {
            builder.addParameter(key, (JobParameter) value);
        } else {
            System.out.println(value.getClass());
        }
    }

}
