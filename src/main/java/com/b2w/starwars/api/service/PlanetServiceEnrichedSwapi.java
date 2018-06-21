package com.b2w.starwars.api.service;

import com.b2w.starwars.api.external.SwapiClient;
import com.b2w.starwars.api.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Service witch wraps {@link PlanetServiceReact} and enrich the return with swapi external API, using
 * {@link SwapiClient}.
 */
@Qualifier("enriched")
@Service
public class PlanetServiceEnrichedSwapi implements PlanetService {

    @Autowired
    @Qualifier("raw")
    private PlanetService planetService;

    @Autowired
    private SwapiClient swapiClient;

    private Mono<Planet> apparisionsInFilms(Mono<Planet> planet) {
         Mono<Integer> apparisions = planet.flatMap(p -> swapiClient.catchMoviewAppearances(p.getName())
                .map(ret -> {
                    if (ret == Mono.empty()) return 0;
                    LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>)ret;
                    Integer appearances = ((List)lhm.get("films")).size();
                    return appearances;
                }));

        Mono<Planet> pl = apparisions.flatMap(a -> planet.map(p -> {
            p.setMoviesAppearances(a);
            return p;
        }));

        return pl;
    }

    @Override
    public Mono<Planet> add(Planet planet) {
        return apparisionsInFilms(planetService.add(planet));
    }

    @Override
    public Flux<Planet> searchAll() {
        return planetService.searchAll()
                .flatMap(p -> apparisionsInFilms(Mono.just(p)));
    }

    @Override
    public Flux<Planet> searchName(String name) {
        return planetService.searchName(name)
                .flatMap(p -> apparisionsInFilms(Mono.just(p)));
    }

    @Override
    public Mono<Planet> searchId(String id) {
        return apparisionsInFilms(planetService.searchId(id));
    }

    @Override
    public Mono<Void> remove(String id) {
        return planetService.remove(id);
    }

}
