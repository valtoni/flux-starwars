package com.b2w.starwars.api.external;

import lombok.Data;

import java.util.List;

/**
 * Represents a Swapi response.
 * @param <T>
 */
@Data
public class SwapiPagedResult<T> {

    private List<T> results;
    private String next;
    private Integer count;
    private String previous;

}
