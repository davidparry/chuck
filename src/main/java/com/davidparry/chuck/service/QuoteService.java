package com.davidparry.chuck.service;

import reactor.core.publisher.Mono;

public interface QuoteService {

    Mono<String> generateQuote(String name);
}
