package com.rahand.idea.channel.enums;

public class BoomEndpoints {

    public static final String OAUTH_NET_BANK = "/oauth/token";
    public static final String DEPOSIT_BASE_ENDPOINT = "/deposits";
    public static final String DEPOSIT_DETAILS = DEPOSIT_BASE_ENDPOINT + "/{$deposit_number}";
    public static final String DEPOSIT_STATEMENTS = DEPOSIT_BASE_ENDPOINT + "/{$deposit_number}/statements";
}
