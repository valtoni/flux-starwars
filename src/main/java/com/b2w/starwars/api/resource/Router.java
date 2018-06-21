package com.b2w.starwars.api.resource;

import com.b2w.starwars.api.model.Planet;
import com.b2w.starwars.api.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

/**
 * This is the router of our API, the principal class of the system, capturing and redirecting
 * the request for the resources provided by {@link #routes(Router)} function bean.
 * Acting as front-end, this class cannot contain any non routing task, to be compliant with the
 * architecture. In next phase (Services) we can integrate with another external resources.
 */
@Component
public class Router {

    @Qualifier("enriched")
    @Autowired
    private PlanetService planetService;

    /**
     * Add a planet with name, climate and terrain.
     * @param serverRequest server-side http request containing the planet
     * @return ServerResponse, containing added planet (with id)
     */
    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        Mono<Planet> planetMono = serverRequest.bodyToMono(Planet.class);

        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(planetMono.flatMap(planetService::add), Planet.class)
                .doOnError(throwable -> new IllegalStateException("I find your lack of faith disturbing. And i can't save your planet."));
    }

    /**
     * Obtain all planets persisted in our database.
     * @param serverRequest empty server-side http request
     * @return ServerResponse, containing a list of planets
     */
    public Mono<ServerResponse> searchAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(planetService.searchAll(), Planet.class)
                .doOnError(throwable -> new IllegalStateException("Help me, Obi-Wan Kenobi. You’re my only hope. All our planets are falling down"));
    }

    /**
     * Search a list of planets name aware.
     * @param serverRequest server-side http request containing a name
     * @return ServerResponse, containing a list of planets matched from name
     */
    public Mono<ServerResponse> searchName(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("name");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(planetService.searchName(name), Planet.class)
                .doOnError(throwable -> new IllegalStateException("Now, young Skywalker, you will die because we've got an error catching something about your planet."));
    }

    /**
     * Search a planet by informed id.
     * @param serverRequest server-side http request containing an ID of searched planet
     * @return ServerResponse, containing planet of id in request
     */
    public Mono<ServerResponse> searchId(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(planetService.searchId(id), Planet.class)
                .doOnError(throwable -> new IllegalStateException("Do. Or do not. There is no try. An Id must be destroyed..."));
    }

    /**
     * Remove a planet by id.
     * @param serverRequest server-side http request contaning an ID to delete planet
     * @return ServerResponse, with empty value
     */
    public Mono<ServerResponse> remove(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .body(planetService.remove(name), Void.class)
                .doOnError(throwable -> new IllegalStateException("Do. Or do not. There is no try. And i don't was able do delete your planet."));
    }


    /**
     * Method witch provides the instance router that route the requests.
     * @param router instance injected by spring
     * @return instance used to routing
     */
    @Bean
    RouterFunction<?> routes(Router router) {
        return RouterFunctions
                .route(RequestPredicates.GET("/planets"), router::searchAll)
                .andRoute(RequestPredicates.GET("/planets/name/{name}"), router::searchName)
                .andRoute(RequestPredicates.GET("/planets/id/{id}"), router::searchId)
                .andRoute(RequestPredicates.DELETE("/planets/{id}"), router::remove)
                .andRoute(RequestPredicates.POST("/planets").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), router::add);
    }

}
