package com.rahand.idea.channel.proxy.client.rest.boomsandbox;

import com.rahand.idea.channel.config.BoomConfig;
import com.rahand.idea.channel.enums.Bank;
import com.rahand.idea.channel.enums.BoomEndpoints;
import com.rahand.idea.channel.enums.ChannelRequestHeaderKeys;
import com.rahand.idea.channel.exception.ChannelRequestException;
import com.rahand.idea.channel.proxy.client.rest.WebClientInstance;
import com.rahand.idea.channel.utils.LogUtils;
import com.rahand.idea.channel.utils.Utils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.nio.charset.StandardCharsets;
import java.util.Map;


import static com.rahand.idea.channel.proxy.client.rest.boomsandbox.BoomRequestHeader.setRequestHeader;

@Component
public class BoomService {

    private BoomConfig boomConfig;
    private WebClientInstance webClient;
    private Logger logger = LogUtils.getLogger();

    @Autowired
    public BoomService(BoomConfig boomConfig, WebClientInstance webClient) {
        this.boomConfig = boomConfig;
        this.webClient = webClient;
    }

    public String getAccessToken(Map<String, String> reqHeader) {
        logger.info("FARABOOM => getAccessToken() Service call started ...");

        Bank bank = Utils.getBankEnumFromMapByKey(reqHeader, ChannelRequestHeaderKeys.BANK);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", boomConfig.getNetBank().getUsername());
        formData.add("password", boomConfig.getNetBank().getPassword());

        return webClient.getWebClientBuilderWithBasicAuthentication(boomConfig, MediaType.APPLICATION_FORM_URLENCODED_VALUE, bank)
                .post()
                .uri(BoomEndpoints.OAUTH_NET_BANK)
                .headers(httpHeaders -> {
                    httpHeaders.addAll(setRequestHeader(bank, boomConfig));
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .elapsed()  // map the stream's time into our streams data
                .doOnNext(tuple -> logger.info("FARABOOM => getAccessToken() ****** Response time took {} Milliseconds ******", tuple.getT1()))

                .map(Tuple2::getT2) // after outputting the measurement, return the data only
                .block();
    }

    public String getDepositInfo(Map<String, String> reqHeader, String reqBody, String accessToken) {
        logger.info("FARABOOM => getDepositInfo() Service call started ...");

        String deposit_number = (String) Utils.getJsonValueByIgnoredCaseKey(reqBody, "deposit_number");
        if (deposit_number == null) {
            throw new ChannelRequestException("Request Body should contain 'deposit_number'.");
        }

        Bank bank = Utils.getBankEnumFromMapByKey(reqHeader, ChannelRequestHeaderKeys.BANK);

        return webClient.getWebClientBuilderWithNoAuthentication(boomConfig, MediaType.APPLICATION_JSON_VALUE, bank)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(BoomEndpoints.DEPOSIT_DETAILS)
                        .build(deposit_number))
                .headers(httpHeaders -> {
                    httpHeaders.addAll(setRequestHeader(bank, boomConfig));
                    httpHeaders.setBearerAuth(accessToken);
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .elapsed()  // map the stream's time into our streams data
                .doOnNext(tuple -> logger.info("FARABOOM => getDepositInfo() **** Response time took {} Milliseconds", tuple.getT1()))
                .map(Tuple2::getT2) // after outputting the measurement, return the data only
                .block();
    }

    public String getDepositStatements(Map<String, String> reqHeader, String reqBody, String accessToken) {
        logger.info("FARABOOM => getDepositStatements() Service call started ...");

        String deposit_number = (String) Utils.getJsonValueByIgnoredCaseKey(reqBody, "deposit_number");
        if (deposit_number == null) {
            throw new ChannelRequestException("Request Body should contain 'deposit_number'.");
        }
        Bank bank = Utils.getBankEnumFromMapByKey(reqHeader, ChannelRequestHeaderKeys.BANK);

        return webClient.getWebClientBuilderWithNoAuthentication(boomConfig, MediaType.APPLICATION_JSON_VALUE, bank)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(BoomEndpoints.DEPOSIT_STATEMENTS)
                        .build(deposit_number))
                .headers(httpHeaders -> {
                    httpHeaders.addAll(setRequestHeader(bank, boomConfig));
                    httpHeaders.setBearerAuth(accessToken);
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(reqBody), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .elapsed()  // map the stream's time into our streams data
                .doOnNext(tuple -> logger.info("FARABOOM => getDepositStatements() **** Response time took {} Milliseconds", tuple.getT1()))
                .map(Tuple2::getT2) // after outputting the measurement, return the data only
                .block();
    }

    public String getDepositBalance(String depositInfo) {

        Long operation_time = (Long) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "operation_time");
        String deposit_number = (String) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "deposit_number");
        String deposit_status = (String) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "deposit_status");
        Long balance = (Long) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "balance");
        Integer blocked_amount = (Integer) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "blocked_amount");
        String currency = (String) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "currency");
        Long available_balance = (Long) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "available_balance");
        Long extra_available_balance = (Long) Utils.getJsonValueByIgnoredCaseKey(depositInfo, "extra_available_balance");

        String depositBalanceInfo = "{\n" +
                "    \"operation_time\":" + operation_time + ",\n" +
                "    \"deposit_number\": \"" + deposit_number + "\",\n" +
                "    \"deposit_status\": \"" + deposit_status + "\",\n" +
                "    \"balance\":" + balance + ",\n" +
                "    \"blocked_amount\":" + blocked_amount + ",\n" +
                "    \"currency\": \"" + currency + "\",\n" +
                "    \"available_balance\":" + available_balance + ",\n" +
                "    \"extra_available_balance\":" + extra_available_balance + "\n" +
                "}";

        return depositBalanceInfo;
    }
}
