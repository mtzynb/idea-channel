import com.rahand.idea.channel.enums.Bank;
import com.rahand.idea.channel.enums.BoomEndpoints;
import com.rahand.idea.channel.exception.CustomWebClientResponseException;
import com.rahand.idea.channel.exception.ErrorResponse;
import com.rahand.idea.channel.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


public class Test {

    private Logger logger = LogUtils.getLogger();

    public WebClient getWebClientBuilderWithNoAuthentication(String mediaType, Bank bank) {
        return WebClient.builder()
                .baseUrl("https://api.sandbox.faraboom.co/v1/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "Rahand Idea Channel")
                .filter(logRequest())
                .filter(errorResponseHandlingFilterAndLogResponseFilter(bank))
                .build();
    }

    public String getDepositDetails(String accessToken) {

        logger.info("FARABOOM => getDepositDetails() Service call started ...");


        return this.getWebClientBuilderWithNoAuthentication(MediaType.APPLICATION_JSON_VALUE, Bank.BOOMIR)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(BoomEndpoints.DEPOSIT_DETAILS)
                        .build("020125580100"))
                .headers(httpHeaders -> {
                    httpHeaders.addAll(setRequestHeader());
                    httpHeaders.setBearerAuth(accessToken);
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
//                .exchangeToMono(response -> {
//                    if (response.statusCode().equals(HttpStatus.OK)) {
//                        return response.bodyToMono(String.class);
//                    } else if (response.statusCode().is4xxClientError()) {
//                        return response.bodyToMono(String.class).flatMap(body -> { body;});
//                    } else {
//                        // Turn to error
//                        return response.createException().flatMap(Mono::error);
//                    }
//                }).block();
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, res -> {
//                                    res.bodyToMono(String.class)
//                                    .flatMap(errorDetails -> {
//                                        logger.error("errorDetails -- Error Response Body is {}", errorDetails);
//                                         Mono.error(new CustomWebClientException(res.statusCode().value(), errorDetails));
//                                    });
//
//                        }
//                            logger.error("BODYYYYYYY{}", res.logPrefix());
//                            res.toEntity(String.class).subscribe(
//                                    entity -> logger.error("Client error {}", entity.getBody())
//                            );
//                            return Mono.error(new HttpClientErrorException(res.statusCode()));
//                        }
//                )
//                .onStatus(HttpStatus::is5xxServerError, response -> {
//                    logger.error("FARABOOM => getDepositDetails(): 5xx error: {}", response.statusCode());
//                    return Mono.error(new InvalidRequestJsonException("5xx"));
//                })
                .bodyToMono(String.class)
//                .onErrorResume(CustomWebClientException.class,
//                        ex -> ex.getStatus().is4xxClientError() ? Mono.error(ex) : Mono.error(ex))
                .block();
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
                logger.info("Req Header => {}={}", name, value);
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
        logger.debug("Returned status code {} ({})", status.value(), status.getReasonPhrase());
    }

    private Mono<ClientResponse> logResBody(ClientResponse response) {

        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> {
                        logger.debug("Error Response Body is {}", body);
                        return Mono.just(response);
                    });
        } else {
            return Mono.just(response);
        }
    }

    //#################################################################
    protected static MultiValueMap<String, String> setRequestHeader() {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("Accept-Language", "fa");
        headers.add("Device-Id", "192.168.1.1");
        headers.add("App-Key", "13774");
        headers.add("Token-Id", "p8VOFaS7w5MB62bUtDQ46uvuuwxG1csIA5AXYIgp3LsXfoR9xcc36nzVrFMeYez9nEty1RAqxuozHzAqCDBc8yKklk");
        headers.add("CLIENT-IP-ADDRESS", "192.168.1.1");
        headers.add("CLIENT-PLATFORM-TYPE", "WEB");
        headers.add("CLIENT-DEVICE-ID", "192.168.1.1");
        headers.add("Bank-Id", "BOOMIR");
        headers.add("CLIENT-USER-ID", "09126705277");
        headers.add("CLIENT-USER-AGENT", "Rahand Smart Idea Platform");

        return headers;
    }


    public ExchangeFilterFunction errorResponseHandlingFilterAndLogResponseFilter(Bank bank) {

        return ExchangeFilterFunction.ofResponseProcessor(response -> {

            logResStatus(response);
            logResHeaders(response);

            if (response.statusCode() != null && (response.statusCode().is5xxServerError() || response.statusCode().is4xxClientError())) {
                return response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            logger.error(bank + " ---->  Error Response Body is {}", errorBody);
                            return Mono.error(new CustomWebClientResponseException(response.statusCode(), new ErrorResponse(response.rawStatusCode(), errorBody, bank)));
                        });
            } else {
                return Mono.just(response);
            }
        });
    }
}
