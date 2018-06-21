package com.b2w.starwars.api.external;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.URI;

/**
 * Reactive Swapi Client, to make a non-block Netty query in network.
 */
@Component
public class ReactiveSwapiClient implements SwapiClient {

    Logger log = LoggerFactory.getLogger(ReactiveSwapiClient.class);

    private static final String SWAPI_BASE_URI = "b2w.starwars.swapi.uri";
    private static final String SWAPI_CLIENT = "b2w.starwars.swapi.client";

    private RateLimiter rateLimiter;

    public static final Scheduler RATE_LIMIT_SCHEDULER = Schedulers.newSingle("RateLimitThread", true);

    private WebClient webClient;

    private final String SWAPI_URL;

    private UriBuilderFactory uriBuilderFactory;

    /**
     * Create a Reactive client based on spring Environment.
     * @param env
     */
    public ReactiveSwapiClient(Environment env) {
        SWAPI_URL = env.getProperty(SWAPI_BASE_URI);
        String swapiClientName = env.getProperty(SWAPI_CLIENT);
        webClient = WebClient
                .builder()
                .baseUrl(SWAPI_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, swapiClientName)
                .build();
        uriBuilderFactory = new DefaultUriBuilderFactory(SWAPI_URL);
    }

    private UriBuilder urlBuilder(String urlPath) {
        UriBuilder uriBuilder = uriBuilderFactory.builder().path(urlPath);
        log.debug("Mounted URI: {}", uriBuilder.toString());
        return uriBuilder;
    }

    /**
     * Create a primitive request to be used internally.
     * @param uri
     * @return response from client
     */
    private Mono<ClientResponse> getRequest(URI uri) {
        return this.webClient
                .get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .doOnSuccessOrError((clientResponse, throwable) -> log.info("Request to {} was terminated", uri.toString()))
                .retry(5, throwable -> {
                    log.error("Swapi request to {} was failed ({})", uri.toString(), throwable);
                    return throwable instanceof IOException;
                });
    }

    private Mono<SwapiPagedResult> getRequestWrapped(URI uri) {
        return getRequest(uri).flatMap(clientResponse -> clientResponse.bodyToMono(SwapiPagedResult.class));
    }

    /**
     * Catch movie appearances (warning: entire object will reply here).
     * TODO A typed {@link SwapiPagedResult} must be returned here, but the class {@link WebClient} must be
     * righted to generate correct map.
     * For this opportunity in B2W i'll leave things as they are.
     *
     * @param name planet name
     * @return Object, possibly a Mono with LinkedHashMap inside, with all unmaped json return
     */
    public Mono<Object> catchMoviewAppearances(String name) {
        return getRequestWrapped(urlBuilder("/planets/").queryParam("search", name).build())
                .map(resp -> resp == null ? Mono.empty() : resp.getResults().size() > 0 ? resp.getResults().get(0) : Mono.empty());
    }

}
