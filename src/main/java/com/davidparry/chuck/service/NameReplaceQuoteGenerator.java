package com.davidparry.chuck.service;

import com.davidparry.chuck.controller.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;
@Service
public class NameReplaceQuoteGenerator implements QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(NameReplaceQuoteGenerator.class);

    private final WebClient client;

    public NameReplaceQuoteGenerator(WebClient webClient) {
        client = webClient;
    }

    public Mono<String> generateQuote(String name) {
        return client.get().retrieve().bodyToMono(Quote.class)
                     .map(produceReplaceFunction(name));
    }

    Function<Quote,String>  produceReplaceFunction(String name) {
        return quote ->
            quote.value().replace("Chuck Norris", name);
    }

}
