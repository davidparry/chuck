package com.davidparry.chuck.service;

import com.davidparry.chuck.controller.Quote;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChuckQuoteGenerator implements QuoteService {

     private final WebClient client;

    public ChuckQuoteGenerator(WebClient webClient) {
        client = webClient;
    }

    @Override
    public Mono<String> generateQuote(String name) {
        return client.get().retrieve().bodyToMono(Joke.class).map(Joke::value);
    }
}
