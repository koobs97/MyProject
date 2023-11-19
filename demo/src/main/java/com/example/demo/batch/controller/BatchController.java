package com.example.demo.batch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
public class BatchController {
    
    @Autowired JobLauncher jobLauncher; // job 실행용 런처

    @Autowired JobRegistry jobRegistry; // 멀티잡 구현

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @GetMapping("/batch001")
    @ResponseBody
    public void selectAll() throws Exception {

        //JobParameter jobParameter = new JobParameter(System.currentTimeMillis());
        Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
        jobLauncher.run(jobRegistry.getJob("Batch001Job"), new JobParameters(parameters));
    }
}
