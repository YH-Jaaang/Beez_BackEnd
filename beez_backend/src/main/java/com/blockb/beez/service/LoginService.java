package com.blockb.beez.service;

import com.blockb.beez.dao.LoginDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;
    
    public String userLogin(String id){
        String loginDb = loginDao.userLogin(id);
        if(loginDb == null){
            return "fail";
        }
        else{
            return loginDb;
        }
    }
}
