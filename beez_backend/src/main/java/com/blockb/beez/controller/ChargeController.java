package com.blockb.beez.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import com.blockb.beez.dto.ChargeDto;
import com.blockb.beez.service.ChargeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.exceptions.TransactionException;

@CrossOrigin("*")
@RestController
@RequestMapping("/charge")
public class ChargeController {
    @Autowired
    ChargeService chargeService;

    //유저 토큰 충전 @RequestBody ChargeDto chargeDto
    @PostMapping("/amount")
    public String getProducts(@RequestParam("address")String address, @RequestParam("charge")String chargeAmount) throws IOException, ExecutionException, InterruptedException {
        System.out.println("address : " + address+"\n"+"chargeAmount1 :" +chargeAmount);
        chargeService.chargeCheck(address, Integer.parseInt(chargeAmount));
        return "성공";
    }
    //발행량 확인
    @GetMapping("/hello")
    public int hello() throws IOException, ExecutionException, InterruptedException, TransactionException{
        return chargeService.totalSupply();
    }
    //회원가입시 이더 전송
    @PostMapping("/ethSend")
    public String ethSend(String toAddress) throws IOException{
        chargeService.ethSend(toAddress);
        return "이더 전송 성공";
    }

}
