package com.example.demo.batch.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@ConfigurationProperties(prefix = "batch.common")
public class BatchProperties {
    
    private String IN_FILE_PATH = "C:/VSCode/File/sample-list.csv";
    private String OUT_FILE_PATH = "";
}
