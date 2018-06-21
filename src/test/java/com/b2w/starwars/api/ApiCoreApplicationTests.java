package com.b2w.starwars.api;

import com.b2w.starwars.api.model.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiCoreApplicationTests {


    @Autowired
    private WebTestClient webTestClient;

    /**
     * Test a simple mechanism of our bw2 api.
     */
    @Test
    public void simpleEmptyFind() {
        webTestClient
                .get().uri("/planets")
                .accept(MediaType.ALL)
                .exchange()
                .expectStatus().isOk();
    }

    /**
     * Test a full of set client.
     * TODO if test was failed, the database will still populated. We must proceed with the tests to delete the data or put down the docker container.
     */
    void fullCrudTest(String id, String name, String climate, String terrain, Integer moviesAppearances) {
        Planet alderaan = new Planet(id, name, climate, terrain);
        webTestClient
                .post().uri("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(alderaan), Planet.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(name)
                .jsonPath("$.moviesAppearances").isEqualTo(moviesAppearances)
                .jsonPath("$.climate.name").isEqualTo(climate)
                .jsonPath("$.terrain.name").isEqualTo(terrain);
        webTestClient
                .get().uri("/planets/name/{name}", name)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0]id").isEqualTo(id)
                .jsonPath("$.[0]name").isEqualTo(name)
                .jsonPath("$.[0]moviesAppearances").isEqualTo(moviesAppearances)
                .jsonPath("$.[0]climate.name").isEqualTo(climate)
                .jsonPath("$.[0]terrain.name").isEqualTo(terrain);
        webTestClient
                .get().uri("/planets/id/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(name)
                .jsonPath("$.moviesAppearances").isEqualTo(moviesAppearances)
                .jsonPath("$.climate.name").isEqualTo(climate)
                .jsonPath("$.terrain.name").isEqualTo(terrain);
        webTestClient
                .delete().uri("/planets/{id}", id)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void alderaan() {
        String id = "5b26a9937a27e2784889b3ab";
        String name = "Alderaan";
        String climate = "temperate";
        String terrain = "grasslands";
        Integer moviesAppearances = 2;
        fullCrudTest(id, name, climate, terrain, moviesAppearances);
    }

    @Test
    public void yavinIV() {
        String id = "5b26a9937a27e2784889b3ad";
        String name = "Yavin IV";
        String climate = "temperate, tropical";
        String terrain = "jungle, rainforests";
        Integer moviesAppearances = 1;
        fullCrudTest(id, name, climate, terrain, moviesAppearances);
    }

    @Test
    public void hoth() {
        String id = "5b26a9937a27e2784889b3ae";
        String name = "Hoth";
        String climate = "frozen";
        String terrain = "tundra, ice caves, mountain ranges";
        Integer moviesAppearances = 1;
        fullCrudTest(id, name, climate, terrain, moviesAppearances);
    }

    @Test
    public void dagobah() {
        String id = "5b26a9937a27e2784889b3af";
        String name = "Dagobah";
        String climate = "murky";
        String terrain = "swamp, jungles";
        Integer moviesAppearances = 3;
        fullCrudTest(id, name, climate, terrain, moviesAppearances);
    }



}
