package com.example.demo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.web.dao.UserDao;
import com.example.demo.web.dto.ResultData;
import com.example.demo.web.dto.UserData;

@Service
public class UserServiceImp implements UserService {

    private ResultData rData;

    @Autowired UserDao uDao;

    public ResultData findAll(){
        rData = new ResultData();
        List<UserData> resultList = uDao.findAll();
        if(resultList != null){
            rData.setState(true);
            rData.setResult(resultList);
        }
        else{
            rData.setState(false);
        }


        return rData;
    }
    
}
