package com.blockb.beez.service;

import java.util.concurrent.ExecutionException;

import com.blockb.beez.dao.ChargeDao;
import com.blockb.beez.dao.TransactionDao;
import com.blockb.beez.dao.UserDao;
import com.blockb.beez.dto.ContractCADto;
import com.blockb.beez.dto.HistoryDto;
import com.blockb.beez.dto.UserDto;
import com.blockb.beez.exception.UserNotFoundException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

@Service
public class ChargeService { 
    private TransactionDao transactionDao;
    @Autowired
    ChargeDao chargeDao;
    @Autowired
    UserDao userDao;
    ContractCADto addressDto = new ContractCADto();

    public ChargeService(TransactionDao transactionDao)
    {
        this.transactionDao = transactionDao;
    }

    //유저 토큰 충전
    public List<String> chargeCheck(String userAddress, int amount) throws IOException, ExecutionException, InterruptedException, TransactionException {
        
        Function function = new Function("chargeCheck",
                                         Arrays.asList(new Address(userAddress), new Uint128(amount)),
                                         Collections.emptyList());

        // 2. sendTransaction
        String txHash = transactionDao.ethSendTransaction(function, null);
        
        //return Hash값
        List<String> transaction = new ArrayList<>();
        transaction.add(txHash);

        // 7. getReceipt
        transactionDao.getReceipt(txHash);

        // 8. DB CHARGE HISTORY 남기기
        Function function2 = new Function("incentiveCheck",
                                        Arrays.asList(new Address(userAddress)),
                                        Arrays.asList(new TypeReference<Uint256>() {}));

        int incentiveCheck =  ((BigInteger)transactionDao.ethCall(userAddress, function2)).intValue();

        Map<String, String> history = new HashMap<String, String>();
        history.put("userAddress", userAddress);
        history.put("chargeAmount", String.valueOf(amount));
        history.put("chargeInc", String.valueOf(incentiveCheck));
        history.put("txHash", txHash);
        chargeDao.chargeHistory(history);

        return transaction;
    }
    
    //유저 계좌 번호 출력
    public UserDto findByUserAccount(String email) {
        return userDao.findByUserAccount(email)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }

    //유저 History 출력
    public List<HistoryDto> historyList(Long userId) {
        return chargeDao.historyList(userId);
    }

    //회원가입시 이더 전송
    public void ethSend(String toAddress) throws IOException {
        String txHash = transactionDao.ethSend(toAddress);

        TransactionReceipt receipt = transactionDao.getReceipt(txHash);
        System.out.println("receipt = " + receipt);
    }
}
