package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.web.dto.ResultData;
import com.example.demo.web.service.UserService;

@RestController
public class DataController {

    @Autowired UserService uService;

    @GetMapping("/")
    public String home(){
        return "data 준비중";
    }

    @PostMapping("/findAll")
    public ResultData findAll(){

        return uService.findAll();
    }
}
