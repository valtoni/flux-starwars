package com.b2w.starwars.api.external;

import reactor.core.publisher.Mono;

/**
 * This class queries a external api https://swapi.co.
 */
public interface SwapiClient {

    // ok, ok... Mono dependency isn't to appear here. Please, this is only for demonstrations purposes ;)
    Mono<Object> catchMoviewAppearances(String name);

}
