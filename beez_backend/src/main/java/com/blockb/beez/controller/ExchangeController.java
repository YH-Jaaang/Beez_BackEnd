package com.blockb.beez.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.blockb.beez.dto.ExchangeDto;
import com.blockb.beez.dto.UserDto;
import com.blockb.beez.dto.WithdrawalDto;
import com.blockb.beez.service.ExchangeService;
import com.blockb.beez.dto.response.BaseResponse;
import com.blockb.beez.dto.response.SingleDataResponse;
import com.blockb.beez.exception.DuplicatedUsernameException;
import com.blockb.beez.exception.UserNotFoundException;
import com.blockb.beez.service.ResponseService;
import com.blockb.beez.service.WithdrawalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ExchangeController {
    @Autowired
    ResponseService responseService;
    @Autowired
    ExchangeService exchangeService;
    @Autowired
    WithdrawalService withdrawalService;

    //소상공인 환전
    @PostMapping("/exchange/amount")
    public ResponseEntity exchange(@RequestBody ExchangeDto exchangeDto) throws IOException, ExecutionException, InterruptedException {
        ResponseEntity responseEntity = null;
        try {
            System.out.println("address : "+exchangeDto.getAddress()+"\n"+"id : " + exchangeDto.getEmail()+"\n"+"exchangeAmount :" +exchangeDto.getExchange());
            
            List<String> exchangeInfo = exchangeService.exchange(exchangeDto.getAddress(), Integer.parseInt(exchangeDto.getExchange()));
            SingleDataResponse<List<String>> response = responseService.getSingleDataResponse(true, "환전 성공", exchangeInfo);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DuplicatedUsernameException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    //소상공인 출금
    @PostMapping("/withdrawal/amount")
    public ResponseEntity withdrawal(@RequestBody WithdrawalDto withdrawalDto) throws IOException, ExecutionException, InterruptedException {
        ResponseEntity responseEntity = null;
        try {
            System.out.println("address : "+withdrawalDto.getAddress()+"\n"+"id : " + withdrawalDto.getEmail()+"\n"+"withdrawalAmount :" + withdrawalDto.getWithdrawal());
            
            List<String> withdrawalInfo = withdrawalService.withdrawal(withdrawalDto.getAddress(), Integer.parseInt(withdrawalDto.getWithdrawal()));
            SingleDataResponse<List<String>> response = responseService.getSingleDataResponse(true, "환전 성공", withdrawalInfo);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DuplicatedUsernameException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @PostMapping("/withdrawal/account")
    public ResponseEntity findByUserAccount(@RequestBody WithdrawalDto withdrawalDto){
        ResponseEntity responseEntity = null;

        try {
            UserDto findUser = withdrawalService.findByUserAccount(withdrawalDto.getEmail());

            SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "조회 성공", findUser);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(UserNotFoundException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
         return responseEntity;
    }
}
