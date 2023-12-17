package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h3>
 * {@link DemoApplication} : 스프링부트의 메인어플리케이션 
 * </h3>
 * <ul>
 * 	<li> {@code @EnableBatchProcessing} 어노테이션을 통해 배치 기능 활성화
 * 	<li> {@code @EnableScheduling} 어노테이션을 통해 배치 스케줄링 기능 활성화
 *  <li> {@code @SpringBootApplication} Spring Boot 애플리케이션을 정의하는 데 사용되는 메타 어노테이션
 * </ul>
 * 
 * spring.batch.job.names의 job.name의 파라미터 검증로직 포함 예정
 * 
 * @Brief   SpringBootApplication
 * @Author  Koo Bon Sang
 * @Date    2023.11.18
 * @Version 1.1.0
 * 
 */

@EnableBatchProcessing	// NOTE : 배치 기능 활성화
@EnableScheduling		// NOTE : 배치 스케줄링 기능 활성화
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class DemoApplication {

	public static void main(String[] args) {
		int exitCode = SpringApplication.exit(SpringApplication.run(DemoApplication.class, args));
		System.exit(exitCode);
	}

	@Value("${spring.batch.job.names:NONE}")
	private String jobNames;

	@PostConstruct
	public void validateJobNames() {
		if(jobNames.isEmpty() || jobNames.equals("NONE")) {
			throw new IllegalArgumentException("실행할 잡 이름을 입력 바랍니다.");
		}
	}

}
