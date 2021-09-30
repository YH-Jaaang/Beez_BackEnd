package com.blockb.beez.service;

import com.blockb.beez.dao.AddressDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressDao addressDao;
    
    public String userLogin(String email){
        String loginDb = addressDao.userLogin(email);
        if(loginDb == null){
            return "fail";
        }
        else{
            return loginDb;
        }
    }
}
