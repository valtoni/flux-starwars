package com.b2w.starwars.api.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * According to wikipedia, "climate is the statistics of weather over long periods of time".
 * Do you agree with that?
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Climate {

    private String name;

}
