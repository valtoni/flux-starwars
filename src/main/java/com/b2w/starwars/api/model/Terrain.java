package com.b2w.starwars.api.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Vertical and horizontal dimensions of land surface (oh, i ❤ wikipedia️).
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Terrain {

    private String name;

}
