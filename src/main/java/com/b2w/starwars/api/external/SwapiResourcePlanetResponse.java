package com.b2w.starwars.api.external;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * It's a Swapi Planet Response! To our request to https://swapi.co/api/planets.
 */
@Data
@EqualsAndHashCode(of = "name", callSuper = false)
public class SwapiResourcePlanetResponse extends SwapiResource {

    private String name;
    private Integer rotationPeriod;
    private Integer orbitalPeriod;
    private Integer diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private Integer surfaceWater;
    private Integer population;
    // TODO Map link into new linked object called "Link" and bring target response
    private Set<String> films;

}
