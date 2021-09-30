package com.blockb.beez.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.blockb.beez.dto.ChargeDto;
import com.blockb.beez.service.ChargeService;
import com.blockb.beez.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.exceptions.TransactionException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ChargeController {
    @Autowired
    ChargeService chargeService;
    @Autowired
    AddressService addressService;

    //유저 토큰 충전
    @PostMapping("/charge/amount")
    public String getProducts(@RequestBody ChargeDto chargeDto) throws IOException, ExecutionException, InterruptedException {
        String address = addressService.userLogin(chargeDto.getEmail());
        System.out.println("address : "+address+"\n"+"id : " + chargeDto.getEmail()+"\n"+"chargeAmount :" +chargeDto.getCharge());
        chargeService.chargeCheck(address, Integer.parseInt(chargeDto.getCharge()));
        return "성공";
    }
    //발행량 확인
    @GetMapping("/hello")
    public List<Integer> hello() throws IOException, ExecutionException, InterruptedException, TransactionException{
        System.out.println("여기봐봐"+chargeService.totalSupply());
        return chargeService.totalSupply();
    }
    //회원가입시 이더 전송
    @PostMapping("/ethSend")
    public String ethSend(String toAddress) throws IOException{
        chargeService.ethSend(toAddress);
        return "이더 전송 성공";
    }
}