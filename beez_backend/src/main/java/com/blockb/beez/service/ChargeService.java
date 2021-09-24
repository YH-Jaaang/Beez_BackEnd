package com.blockb.beez.service;

import java.util.concurrent.ExecutionException;

import com.blockb.beez.dao.TransactionDao;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

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

    public ChargeService(TransactionDao transactionDao)
    {
        this.transactionDao = transactionDao;
    }
    // TEST용
    // public List<ChargeVo> testPrintUsers() {
    //     List<ChargeVo> dd = new ArrayList<ChargeVo>();
    //     return dd;
    // }
    
    public int totalSupply() throws IOException, ExecutionException, InterruptedException, TransactionException {

        // 1. 호출하고자 하는 function 세팅[functionName, parameters]
        Function function = new Function("totalSupply",
                                         Collections.emptyList(),
                                         Arrays.asList(new TypeReference<Uint256>() {}));

        // 2. ethereum을 function 변수로 통해 호출
        return ((BigInteger)transactionDao.ethCall(function)).intValue();
    }
    public void chargeCheck(String userAddress, int amount) throws IOException, ExecutionException, InterruptedException {
        // 1. 호출하고자 하는 function 세팅[functionName, parameters]
//          여러개 넣고자 할 때,
//         Arrays.asList(
//             new Uint256(1),
//             new Utf8String(“1”),
//             new Address(userAddress),
//          ),
        Function function = new Function("chargeCheck",
                                         Arrays.asList(new Address(userAddress), new Uint128(amount)),
                                         Collections.emptyList());

        // 2. sendTransaction
        String txHash = transactionDao.ethSendTransaction(function);

        // 7. getReceipt
        transactionDao.getReceipt(txHash);
    }
    public void ethSend(String toAddress) throws IOException {
        String txHash = transactionDao.ethSend(toAddress);

        TransactionReceipt receipt = transactionDao.getReceipt(txHash);
        System.out.println("receipt = " + receipt);
    }
    
    

}
