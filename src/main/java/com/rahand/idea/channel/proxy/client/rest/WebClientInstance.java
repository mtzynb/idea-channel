package com.rahand.idea.channel.proxy.client.rest;

import com.rahand.idea.channel.config.BoomConfig;
import com.rahand.idea.channel.enums.Bank;
import com.rahand.idea.channel.exception.CustomWebClientResponseException;
import com.rahand.idea.channel.exception.ErrorResponse;
import com.rahand.idea.channel.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Component
public class WebClientInstance {

    private Logger logger = LogUtils.getLogger();

    public WebClient getWebClientBuilderWithBasicAuthentication(BoomConfig boomConfig, String mediaType, Bank bank) {
        return WebClient.builder()
                .baseUrl(boomConfig.getNetBank().getLoginUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, mediaType)
                .defaultHeader(HttpHeaders.USER_AGENT, "Rahand Idea Channel")
                .filter(ExchangeFilterFunctions.basicAuthentication(boomConfig.getAppKey(), boomConfig.getAppSecret()))
                .filter(errorResponseHandlingFilterAndLogResponseFilter(bank))
                .filter(logRequest())
//                .filter(logResponse())
                .build();
    }

    public WebClient getWebClientBuilderWithNoAuthentication(BoomConfig boomConfig, String mediaType, Bank bank) {
        return WebClient.builder()
                .baseUrl(boomConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, mediaType)
                .defaultHeader(HttpHeaders.USER_AGENT, "Rahand Idea Channel")
                .filter(errorResponseHandlingFilterAndLogResponseFilter(bank))
                .filter(logRequest())
//                .filter(logResponse())
                .build();
    }

    // ***************************** Request *****************************

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logReqHeaders(request);

            return Mono.just(request);
        });
    }

    private void logReqHeaders(ClientRequest request) {
        request.headers().forEach((name, values) -> {
            values.forEach(value -> {
                logger.debug("Req Header => {}={}", name, value);
            });
        });
    }

    private void logMethodAndUrl(ClientRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Request ");
        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());

        logger.info(sb.toString());
    }

    // ***************************** Response *****************************
    public ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logResStatus(response);
            logResHeaders(response);

            return logResBody(response);
        });
    }

    private void logResHeaders(ClientResponse response) {
        response.headers().asHttpHeaders().forEach((name, values) -> {
            values.forEach(value -> {
                logger.debug("Res Header => {}={}", name, value);
            });
        });
    }

    private void logResStatus(ClientResponse response) {
        HttpStatus status = response.statusCode();
        logger.info("Returned status code {} ({})", status.value(), status.getReasonPhrase());
    }

    private Mono<ClientResponse> logResBody(ClientResponse response) {
        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> {
                        logger.info("Error Response Body is {}", body);
                        return Mono.just(response);
                    });
        } else {
            return Mono.just(response);
        }
    }

    public ExchangeFilterFunction errorResponseHandlingFilterAndLogResponseFilter(Bank bank) {

        return ExchangeFilterFunction.ofResponseProcessor(response -> {

            logResStatus(response);
            logResHeaders(response);

            if (response.statusCode() != null && (response.statusCode().is5xxServerError() || response.statusCode().is4xxClientError())) {
                return response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            logger.info(bank.getBankName() + " ---->  Error Response Body is {}", errorBody);
                            return Mono.error(new CustomWebClientResponseException(response.statusCode(), new ErrorResponse(response.rawStatusCode(), errorBody, bank)));
                        });
            } else {
                return Mono.just(response);
            }
        });
    }

}
