package com.blockb.beez.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.blockb.beez.service.HistoryService;
import com.blockb.beez.dto.HistoryDto;
import com.blockb.beez.dto.UserDto;
import com.blockb.beez.dto.response.BaseResponse;
import com.blockb.beez.dto.response.SingleDataResponse;
import com.blockb.beez.exception.DuplicatedUsernameException;
import com.blockb.beez.service.ResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class HistoryController {
    
    @Autowired
    HistoryService historyService;
    @Autowired
    ResponseService responseService;

    //유저 History
    @PostMapping("/history/charge")
    public ResponseEntity history(final Authentication authentication, @RequestBody HistoryDto historyDto) throws IOException, ExecutionException, InterruptedException {
        ResponseEntity responseEntity = null;
        try {

            System.out.println("address : "+historyDto.getChargeAmount()+"\n"+"id : " + historyDto.getChargeInc()+"\n"+"chargeAmount :" +historyDto.getTxHash());

            Long userId = ((UserDto) authentication.getPrincipal()).getUserId();
            historyDto.setUserId(userId);

            historyService.historyCheck(historyDto);

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, "충전 Hisotry 성공", "");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DuplicatedUsernameException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }
    //유저 History
    @GetMapping("/history/list")
    public ResponseEntity historyList(final Authentication authentication) throws IOException, ExecutionException, InterruptedException {
        ResponseEntity responseEntity = null;
        try {
            
            Long userId = ((UserDto) authentication.getPrincipal()).getUserId();
            System.out.println(userId +"wow");
            List<HistoryDto> historyList = historyService.historyList(userId);
            System.out.println(historyList+"wow");
            SingleDataResponse<List<HistoryDto>> response = responseService.getSingleDataResponse(true, "충전List 출력 성공", historyList);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DuplicatedUsernameException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }
}