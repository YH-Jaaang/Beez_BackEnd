package com.blockb.beez.controller;

import java.util.List;

import com.blockb.beez.service.TestService;
import com.blockb.beez.vo.TestVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @PostMapping("/products")
    public List<TestVo> getProducts() {
        System.out.println(testService.testPrintUsers());
        return testService.testPrintUsers();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("input")String wow, @RequestParam("password")String pass ){
        System.out.println(wow+"print"+pass);
        return "dd";
    }
    @GetMapping("/login")
    public void login(){
        System.out.println("print");
    }
}
