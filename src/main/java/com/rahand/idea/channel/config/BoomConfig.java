package com.rahand.idea.channel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "faraboom")
public class BoomConfig {

    @NotNull
    private String baseUrl;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String deviceId;

    @NotNull
    private String tokenId;

    @NotNull
    private String appKey;

    @NotNull
    private String appSecret;

    @NotNull
    private String bankName;

    @NotNull
    private NetBank netBank = new NetBank();

    public static class NetBank {

        @NotNull
        private String loginUrl;

        @NotNull
        private String username;

        @NotNull
        private String password;

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "NetBank{" +
                    "loginUrl='" + loginUrl + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public NetBank getNetBank() {
        return netBank;
    }

    public void setNetBank(NetBank netBank) {
        this.netBank = netBank;
    }

    @Override
    public String toString() {
        return "BoomConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", bankName='" + bankName + '\'' +
                ", netBank=" + netBank +
                '}';
    }
}
