package com.blockb.beez.service;

import java.util.concurrent.ExecutionException;

import com.blockb.beez.dao.HistoryDao;
import com.blockb.beez.dao.TransactionDao;
import com.blockb.beez.dao.UserDao;
import com.blockb.beez.dto.AddressDto;
import com.blockb.beez.dto.UserDto;
import com.blockb.beez.exception.UserNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
public class ChargeService { 
    private TransactionDao transactionDao;
    @Autowired
    HistoryDao historyDao;
    @Autowired
    UserDao userDao;
    AddressDto addressDto = new AddressDto();

    public ChargeService(TransactionDao transactionDao)
    {
        this.transactionDao = transactionDao;
    }

    //유저 토큰 충전
    public List<String> chargeCheck(String userAddress, int amount) throws IOException, ExecutionException, InterruptedException {
        
        // 1. 호출하고자 하는 function 세팅[functionName, parameters]
//          여러개 넣고자 할 때,
//         Arrays.asList(
//             new Uint256(1),
//             new Utf8String(“1”),
//             new Address(userAddress),
//          ),
        Function function = new Function("chargeCheck",
                                         Arrays.asList(new Address(userAddress), new Uint128(amount), new Uint256(1633177666)),
                                         Collections.emptyList());

        // 2. sendTransaction
        String txHash = transactionDao.ethSendTransaction(function, null);
        
        //return Hash값
        List<String> transaction = new ArrayList<>();
        transaction.add(txHash);

        // 7. getReceipt
        transactionDao.getReceipt(txHash);

        // 8. DB CHARGE HISTORY 남기기
        Map<String, String> history = new HashMap<String, String>();
        history.put("userAddress", userAddress);
        history.put("amount", String.valueOf(amount));
        historyDao.chargeHistory(history);

        return transaction;
    }
    public UserDto findByUserAccount(String email) {
        return userDao.findByUserAccount(email)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }

    //회원가입시 이더 전송
    public void ethSend(String toAddress) throws IOException {
        String txHash = transactionDao.ethSend(toAddress);

        TransactionReceipt receipt = transactionDao.getReceipt(txHash);
        System.out.println("receipt = " + receipt);
    }
}
