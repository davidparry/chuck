package com.davidparry.chuck.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Configuration
public class Config {
    public static final String CHUCK_NORRIS_API_URL = "https://api.chucknorris.io/jokes/random?category=dev";
    public static final String SERVER_DOWN_MESSAGE = "OOPS! Look's we are busy come back later";
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean
    public WebClient webClient() {
        return WebClient.builder().filter(ExchangeFilterFunction.ofResponseProcessor(this::renderApiErrorResponse))
                        .baseUrl(CHUCK_NORRIS_API_URL).build();

    }

    private Mono<ClientResponse> renderApiErrorResponse(ClientResponse clientResponse) {
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(ApiErrorResponse.class).doOnNext(apiErrorResponse -> {
                logger.error("Chuck Service ApiErrorResponse: {} ", apiErrorResponse);
            }).flatMap(apiErrorResponse -> Mono.error(
                    new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))));
        }
        return Mono.just(clientResponse);
    }


}
