package com.b2w.starwars.api;

import com.b2w.starwars.api.external.SwapiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests swapiClient flux.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SwapiClientTest {

    @Autowired
    SwapiClient swapiClient;

    /**
     * Test appearances of Alderaan planet in 2 films.
     */
    @Test
    public void testAlderaan() {
        Object returnObject = swapiClient.catchMoviewAppearances("Alderaan").block();
        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>)returnObject;
        Integer appearances = ((List)lhm.get("films")).size();
        assertTrue(2 == appearances);
    }

}
