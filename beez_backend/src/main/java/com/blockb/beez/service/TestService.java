package com.blockb.beez.service;

import java.util.List;

import com.blockb.beez.dao.TestDao;
import com.blockb.beez.vo.TestVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestDao testDao;
    
    public List<TestVo> testPrintUsers() {
        return testDao.testPrintMember();
    }
}
