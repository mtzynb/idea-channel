package com.rahand.idea.channel.proxy.client.rest.boomsandbox;

import com.rahand.idea.channel.config.BoomConfig;
import com.rahand.idea.channel.enums.Bank;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BoomRequestHeader {

    protected static MultiValueMap<String, String> setRequestHeader(Bank bank, BoomConfig boomConfig) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("Accept-Language", "fa");
        headers.add("Device-Id", boomConfig.getDeviceId());
        headers.add("App-Key", boomConfig.getAppKey());
        headers.add("Token-Id", boomConfig.getTokenId());
        headers.add("CLIENT-IP-ADDRESS", "192.168.1.1");
        headers.add("CLIENT-PLATFORM-TYPE", "WEB");
        headers.add("CLIENT-DEVICE-ID", "192.168.1.1");
        headers.add("Bank-Id", bank.getBankName());
        headers.add("CLIENT-USER-ID", "09126705277");
        headers.add("CLIENT-USER-AGENT", "Rahand Smart Idea Platform");

        return headers;
    }
}