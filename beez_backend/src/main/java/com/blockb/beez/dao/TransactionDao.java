package com.blockb.beez.dao;

import java.util.List;
import java.util.Optional;

import com.blockb.beez.dto.ContractCADto;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.springframework.stereotype.Component;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

@Component
public class TransactionDao { 
    ContractCADto addressDto = new ContractCADto();

    private String contract = addressDto.getWonTokenCA();
    String walletPassword = "Blockbbeez1101";
    String walletDirectory = "wallets";
    String walletName ="UTC--2021-09-30T04-17-22.503Z--e96864b245de769fcc64c1e9f4466a0caad526c5";
            
    private Admin web3j = null;

    public TransactionDao()
    {
        web3j = Admin.build(new HttpService("https://ropsten.infura.io/v3/bfe7dce5767341bb8a9d21d0146b8624"));
    }

    public Object ethCall(String userAddress, Function function) throws IOException {

        //2. transaction 제작
        Transaction transaction = Transaction.createEthCallTransaction(
            userAddress,
            contract,
            FunctionEncoder.encode(function)
        );
                                                                                                                                        
        //3. ethereum 호출후 결과 가져오기
        EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        //4. 결과값 decode
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),
                                                            function.getOutputParameters());

        // System.out.println("ethCall.getResult() = " + ethCall.getResult());
        // System.out.println("getValue = " + decode.get(0).getValue());
        // System.out.println("getType = " + decode.get(0).getTypeAsString());

        return decode.get(0).getValue();
        
    }
    /* ########트랜젝션 생성하기######## */
    public String ethSendTransaction(Function function, String privateKey) throws IOException, InterruptedException {
        //private키를 통해서 address값 가져오기
        Credentials credentials = null;
        String transactionHash = null;

        try {
            // 1. 지갑을 암호 해독하고 Credential 객체 생성
            credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+ File.separator + walletName);

            // 2. account에 대한 nonce값 가져오기.
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce =  ethGetTransactionCount.getTransactionCount();
            System.out.println(nonce);
            //gasLimit, gasPrice 너무 낮게 설정 X
            BigInteger gasLimit = BigInteger.valueOf(220000);
            //BigInteger gasPrice = Convert.toWei("2", Unit.GWEI).toBigInteger();
            BigInteger maxPriorityFeePerGas = Convert.toWei("2", Unit.GWEI).toBigInteger();
            BigInteger maxFeePerGas = Convert.toWei("2", Unit.GWEI).toBigInteger();
            long chainId = 0x3; //ropsten NetWork
            BigInteger value = BigInteger.valueOf(0);

            RawTransaction rawTransaction  = RawTransaction.createTransaction(
                       chainId,
                       nonce,   
                       gasLimit,
                       contract,
                       value,
                       FunctionEncoder.encode(function),
                       maxFeePerGas,
                       maxPriorityFeePerGas);
      
            // 트랜잭션 바이트 서명
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);

            String hexValue = Numeric.toHexString(signedMessage);
      
            // 트랜잭션 전송
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            //트랜잭션 에러 확인
            if(ethSendTransaction.getError() != null)
                System.out.println(ethSendTransaction.getError().getMessage());
            
            //트랙잭션 hash값
            transactionHash = ethSendTransaction.getTransactionHash();
      
            // 트랜잭션이 채굴될 때까지 대기
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3j.ethGetTransactionReceipt(transactionHash).send();
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(10000); // Wait 10 sec
            } while(!transactionReceipt.isPresent());
            
        } catch (CipherException e) {
            e.printStackTrace();
        }

        //트랙잭션 hash값 전송
        return transactionHash;
    }
    
    /* ########회원가입 이더 전송######## */
    public String ethSend(String toAddress){
        String transactionHash = null;
        try {
            // 1. 지갑을 암호 해독하고 Credential 객체 생성
            Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+ File.separator + walletName);
            
            System.out.println("Account address: " + credentials.getAddress());
            System.out.println("Balance: " + Convert.fromWei(web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));
      
            // 2. account에 대한 nonce값 가져오기.
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce =  ethGetTransactionCount.getTransactionCount();
            
            // 받는사람 주소
            String recipientAddress = toAddress;
            // 전송할 wei
            BigInteger value = Convert.toWei("1", Unit.ETHER).toBigInteger();
            // 가스비 설정
            BigInteger gasLimit = BigInteger.valueOf(21000);
            //BigInteger gasPrice = Convert.toWei("2", Unit.GWEI).toBigInteger();
            BigInteger maxPriorityFeePerGas = Convert.toWei("2", Unit.GWEI).toBigInteger();
            BigInteger maxFeePerGas = Convert.toWei("2", Unit.GWEI).toBigInteger();
            long chainId = 0x3; //ropsten NetWork
      
            // 3. rawTransaction 생성
            RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                       chainId,
                       nonce,
                       gasLimit,
                       recipientAddress,
                       value,
                       maxPriorityFeePerGas,
                       maxFeePerGas);
      
            // 트랜잭션 바이트 서명
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
      
            // 트랜잭션 전송
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            transactionHash = ethSendTransaction.getTransactionHash();
            System.out.println("transactionHash: " + transactionHash);
      
            // 트랜잭션이 채굴될 때까지 대기
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3j.ethGetTransactionReceipt(transactionHash).send();
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(10000); // Wait 10 sec
            } while(!transactionReceipt.isPresent());
            
            // System.out.println("Transaction " + transactionHash + " was mined in block # " + transactionReceipt.get().getBlockNumber());
            // System.out.println("Balance: " + Convert.fromWei(web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Unit.ETHER));
      
          } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
          } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return transactionHash;
    }
    /* ########트랜젝션 결과 함수######## */
    public TransactionReceipt getReceipt(String transactionHash) throws IOException {

        //8. transaction Hash를 통한 receipt 가져오기.
        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();

        if(transactionReceipt.getTransactionReceipt().isPresent())
        {
            // 9. 결과확인
            System.out.println("transactionReceipt.getResult().getContractAddress() = " +
                               transactionReceipt.getResult());
        }
        else
        {
            System.out.println("transaction complete not yet");
        }

        return transactionReceipt.getResult();
    }

    // private class PersonalLockException extends RuntimeException
    // {
    //     public PersonalLockException(String msg)
    //     {
    //         super(msg);
    //     }
    // }


}
