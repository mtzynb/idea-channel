package com.rahand.idea.channel.service;

import com.rahand.idea.channel.proxy.service.BankService;
import com.rahand.idea.channel.proxy.service.BankServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DepositServiceImpl implements DepositService {

    private BankServiceFactory bankServiceFactory;

    @Autowired
    public DepositServiceImpl(BankServiceFactory bankServiceFactory) {
        this.bankServiceFactory = bankServiceFactory;
    }


    @Override
    public String getDepositInfo(Map<String, String> reqHeader, String reqBody) {
        BankService bankService = bankServiceFactory.getBankService(reqHeader);
        return bankService.getDepositInfo(reqHeader, reqBody);
    }

    @Override
    public String getDepositStatements(Map<String, String> reqHeader, String reqBody) {
        BankService bankService = bankServiceFactory.getBankService(reqHeader);
        return bankService.getDepositStatements(reqHeader, reqBody);
    }

    @Override
    public String getDepositBalance(Map<String, String> reqHeader, String reqBody) {
        BankService bankService = bankServiceFactory.getBankService(reqHeader);
        return bankService.getDepositBalance(reqHeader, reqBody);
    }


}
