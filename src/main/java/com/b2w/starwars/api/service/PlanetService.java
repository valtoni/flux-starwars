package com.b2w.starwars.api.service;

import com.b2w.starwars.api.model.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This service is key a replaceable piece to make all possible to populate and manipulate our models in
 * fashion manner, searching in local database, obtaining external resources and all things to give to the
 * user a wonderful experience, making the entire system more maintainable, following SOLID principles from
 * uncle Bob.
 *
 * Aggregated functionalities:
 * - Add a planet (with name, climate and terrain)
 * - List all planets
 * - Find planet by name
 * - Find planet by ID
 * - Remove Planet
 *
 */
public interface PlanetService {

    /**
     * Add a planet (with name, climate and terrain).
     * @param planet planet to be persisted
     * @return planet added
     */
    Mono<Planet> add(Planet planet);

    /**
     * List all planets.
     * @return all planets from database
     */
    Flux<Planet> searchAll();

    /**
     * List all planets from matching name.
     * @param name name to search
     * @return a list of matched planets
     */
    Flux<Planet> searchName(String name);

    /**
     * Find a planet by id.
     * @param id id of planet
     * @return planet from informed id
     */
    Mono<Planet> searchId(String id);

    /**
     * Remove a planet by id.
     * @param id id of planet
     * @return nothing
     */
    Mono<Void> remove(String id);
    
}
