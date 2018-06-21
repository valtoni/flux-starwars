package com.b2w.starwars.api.repository;

import com.b2w.starwars.api.model.Planet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository layer for querying planet in MongoDB in nonblock way.
 */
public interface PlanetRepository extends ReactiveMongoRepository<Planet, String> {

    /**
     * Find a planet by name.
     * Attempt: this results a Flux.
     * @param name planet's name
     * @return Flux containing found planets
     */
    Flux<Planet> findPlanetByName(String name);

    /**
     * Delete planet by name.
     * @param name planet's name
     */
    void deleteByName(String name);

}
