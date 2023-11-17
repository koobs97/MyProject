package com.example.demo.web.dto;

import lombok.Data;

@Data
public class ResultData {
    private Boolean state;
    private Object result;
    private String message;
}
