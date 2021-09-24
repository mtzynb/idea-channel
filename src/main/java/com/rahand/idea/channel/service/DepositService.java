package com.rahand.idea.channel.service;

import java.util.Map;

public interface DepositService {

    String getDepositInfo(Map<String, String> reqHeader, String reqBody);

    String getDepositBalance(Map<String, String> reqHeader, String reqBody);

    String getDepositStatements(Map<String, String> reqHeader, String reqBody);
}
