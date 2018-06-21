package com.b2w.starwars.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Planet are celestials body gravitating over a another body (yeah, it's from me).
 */
@Document
@Data
@NoArgsConstructor
public class Planet {

    @Id
    private String id;
    private String name;
    private Climate climate;
    private Terrain terrain;
    @Transient
    private Integer moviesAppearances;

    public Planet(String id, String name, String climate, String terrain) {
        this.id = id;
        this.name = name;
        this.climate = new Climate(climate);
        this.terrain = new Terrain(terrain);
    }

    public Planet(String name, String climate, String terrain) {
        this.name = name;
        this.climate = new Climate(climate);
        this.terrain = new Terrain(terrain);
    }

}
