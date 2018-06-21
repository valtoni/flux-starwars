package com.b2w.starwars.api.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Represents a Swapi resource.
 */
@Data
public abstract class SwapiResource {

    private ZonedDateTime created;
    private ZonedDateTime edited;
    private String url;

}
