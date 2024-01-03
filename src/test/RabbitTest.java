package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class RabbitTest {
    @Test
    public void creationRabbit() {
        Tamagotchi rabbit = new Rabbit("Sam", new Place(EPlace.LIVINGROOM), 3);
        assertEquals(rabbit.getName(),"Sam");
        assertEquals(rabbit.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(rabbit.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(rabbit.getDifficulty(), 3);
    }
    
}
