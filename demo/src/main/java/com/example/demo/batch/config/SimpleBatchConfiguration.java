// package com.example.demo.batch.config;

// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.JobExecution;
// import org.springframework.batch.core.JobExecutionListener;
// import org.springframework.batch.core.Step;
// import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
// import org.springframework.batch.core.launch.support.RunIdIncrementer;
// import org.springframework.batch.repeat.RepeatStatus;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @RequiredArgsConstructor
// public class SimpleBatchConfiguration {

//     private final JobBuilderFactory jobBuilderFactory;
//     private final StepBuilderFactory stepBuilderFactory;

//     @Bean
//     public Step simpleStep1(){
//         return stepBuilderFactory
//             .get("simpleStep1")
//             .tasklet((contribution, chunckContext) -> {
//                 System.out.println("########## This is simpleStep1");

//                 return RepeatStatus.FINISHED;
//             })
//             .allowStartIfComplete(true)
//             .build();
//     }

//     @Bean
//     public Step simpleStep2(){
//         return stepBuilderFactory
//             .get("simpleStep2")
//             .tasklet((contribution, chunckContext) -> {
//                 System.out.println("########## This is simpleStep2");

//                 return RepeatStatus.FINISHED;
//             })
//             .allowStartIfComplete(true)
//             .build();
//     }
    
//     @Bean
//     public Step simpleStep3(){
//         return stepBuilderFactory
//             .get("simpleStep3")
//             .tasklet((contribution, chunckContext) -> {
//                 System.out.println("########## This is simpleStep3");

//                 return RepeatStatus.FINISHED;
//             })
//             .allowStartIfComplete(true)
//             .build();
//     }
    
//     @Bean
//     public Job simpleJob(){
//         return jobBuilderFactory
//             .get("simpleJob")
//             .listener(jobExecutionListener())
//             .start(simpleStep1())
//             .next(simpleStep2())
//             .next(simpleStep3())
//             .incrementer(new RunIdIncrementer())
//             .build();
//     }

//     @Bean
//     public JobExecutionListener jobExecutionListener(){
//         JobExecutionListener jobExecutionListener = new JobExecutionListener() {
            
//             @Override
//             public void beforeJob(JobExecution jobExecution) {
//                 // TODO Auto-generated method stub
//                 System.out.println("########## This is before Job");
//             }

//             @Override
//             public void afterJob(JobExecution jobExecution) {
//                 // TODO Auto-generated method stub
//                 System.out.println("########## This is after Job");
//             }
//         };

//         return jobExecutionListener;
//     }
// }
