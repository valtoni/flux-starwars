package com.b2w.starwars.api.service;

import com.b2w.starwars.api.model.Planet;
import com.b2w.starwars.api.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service witch list planets from our simple repository, in raw manner.
 */
@Service
@Qualifier("raw")
public class PlanetServiceReact implements PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Override
    public Mono<Planet> add(Planet planet) {
        return planetRepository.save(planet);
    }

    @Override
    public Flux<Planet> searchAll() {
        return planetRepository.findAll();
    }

    @Override
    public Flux<Planet> searchName(String name) {
        return planetRepository.findPlanetByName(name);
    }

    @Override
    public Mono<Planet> searchId(String id) {
        return planetRepository.findById(id);
    }

    @Override
    public Mono<Void> remove(String id) {
        return planetRepository.deleteById(id);
    }

}
