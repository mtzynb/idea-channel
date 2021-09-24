package com.rahand.idea.channel.proxy.service;

import com.rahand.idea.channel.enums.Bank;
import com.rahand.idea.channel.enums.ChannelRequestHeaderKeys;
import com.rahand.idea.channel.exception.ChannelRequestException;
import com.rahand.idea.channel.proxy.client.rest.boomsandbox.BoomRestClient;
import com.rahand.idea.channel.proxy.client.rest.boomsandbox.BoomService;
import com.rahand.idea.channel.utils.LogUtils;
import com.rahand.idea.channel.utils.Utils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BankServiceFactory {

    private Logger logger = LogUtils.getLogger();

    @Autowired
    BoomService boomService;

    private BoomRestClient getBoomRestClient() {
        return new BoomRestClient(boomService);
    }

    // ---------------------------------------------------------------------------------
    public BankService getBankService(Map<String, String> reqHeader) {
        try {
            logger.info("************ in getBankService **********");
            Bank bank = Utils.getBankEnumFromMapByKey(reqHeader, ChannelRequestHeaderKeys.BANK);
            logger.info("in getBankService --- bank is: " + bank);

            if (bank.value().equals(Bank.SAMAN.value())) {
                return getBoomRestClient();
            } else if (bank.value().equals(Bank.MIDDLE_EAST.value())) {
                return getBoomRestClient();
            } else if (bank.value().equals(Bank.TOURISM.value())) {
                return getBoomRestClient();
            } else if (bank.value().equals(Bank.BOOMIR.value())) {
                return getBoomRestClient();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ChannelRequestException("No Enum Bank const class(Supported Banks: 'SAMAN', 'MIDDLE_EAST', 'TOURISM', 'BOOMIR')");
        }
        throw new ChannelRequestException("Sorry, Idea Channel Doesn't Support the Bank You Requested!(Supported Banks: 'SAMAN', 'MIDDLE_EAST', 'TOURISM', 'BOOMIR')");
    }
}