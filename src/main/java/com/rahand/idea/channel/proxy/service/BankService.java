package com.rahand.idea.channel.proxy.service;


import java.util.Map;

public interface BankService {

    String oauth2(Map<String, String> reqHeader);

    String getDepositInfo(Map<String, String> reqHeader, String reqBody);

    String getDepositBalance(Map<String, String> reqHeader, String reqBody);

    String getDepositStatements(Map<String, String> reqHeader, String reqBody);
}
