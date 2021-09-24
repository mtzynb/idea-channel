package com.rahand.idea.channel.controller;

import com.rahand.idea.channel.enums.ChannelDepositEndpoints;
import com.rahand.idea.channel.enums.LoggerHelp;
import com.rahand.idea.channel.service.DepositService;
import com.rahand.idea.channel.utils.LogUtils;
import com.rahand.idea.channel.utils.WebUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by z.mohammadtabar on 28/08/2021.
 */

@RestController
@RequestMapping("/deposits")
public class DepositController {

    private DepositService depositService;
    private Logger logger = LogUtils.getLogger();

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping(value = "/info", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDepositInfo(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_INFO + " -Post Method- Started...");
        WebUtils.validateChannelReqHeaders(headers);
        String res = depositService.getDepositInfo(headers, body);
        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_INFO + " -Post Method- Ended. Response is: " + res);

        return res;
    }

    @PostMapping(value = "/balance", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDepositBalance(@RequestHeader Map<String, String> headers, @RequestBody String body) {
        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_BALANCE + " -Post Method- Started...");
        WebUtils.validateChannelReqHeaders(headers);
        String res = depositService.getDepositBalance(headers, body);
        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_BALANCE + " -Post Method- Ended. Response is: " + res);
        return res;
    }

    @PostMapping(value = "/statements", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDepositStatements(@RequestHeader Map<String, String> headers, @RequestBody String body) {

        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_STATEMENTS + " -Post Method- Started...");
        WebUtils.validateChannelReqHeaders(headers);
        String res = depositService.getDepositStatements(headers, body);
        logger.info(LoggerHelp.CHANNEL_START_LOG + ChannelDepositEndpoints.DEPOSIT_STATEMENTS + " -Post Method- Ended. Response is: " + res);
        return res;
    }
}
