package com.rahand.idea.channel.proxy.client.rest.boomsandbox;

import com.rahand.idea.channel.proxy.service.BankService;
import com.rahand.idea.channel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BoomRestClient implements BankService {

    private BoomService boomService;

    @Autowired
    public BoomRestClient(BoomService boomService) {
        this.boomService = boomService;
    }

    @Override
    public String oauth2(Map<String, String> reqHeader) {
        return boomService.getAccessToken(reqHeader);
    }

    @Override
    public String getDepositInfo(Map<String, String> reqHeader, String reqBody) {
        String resultJson = oauth2(reqHeader);
        String token = (String) Utils.getJsonValueByIgnoredCaseKey(resultJson, "access_token");
        return boomService.getDepositInfo(reqHeader, reqBody, token);
    }


    @Override
    public String getDepositBalance(Map<String, String> reqHeader, String reqBody) {
        String depositInfo = getDepositInfo(reqHeader, reqBody);
        return boomService.getDepositBalance(depositInfo);
    }


    @Override
    public String getDepositStatements(Map<String, String> reqHeader, String reqBody) {
        String resultJson = oauth2(reqHeader);
        String token = (String) Utils.getJsonValueByIgnoredCaseKey(resultJson, "access_token");
        return boomService.getDepositStatements(reqHeader, reqBody, token);
    }

}
