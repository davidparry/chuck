package com.davidparry.chuck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class DannyController {

    WebClient client = WebClient.create("https://api.chucknorris.io/jokes/random?category=dev");



    @GetMapping("/quote")
    public Mono<Quote> getQuote() {
        return client.get().retrieve().bodyToMono(Quote.class)
                .map(quote -> new Quote(quote.value().replace("Chuck Norris", "Danny")));
    }

}
