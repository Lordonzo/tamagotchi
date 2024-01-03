package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class DogTest {
    @Test
    public void creationDog() {
        Tamagotchi dog = new Dog("Sam", new Place(EPlace.LIVINGROOM), 3);
        assertEquals(dog.getName(),"Sam");
        assertEquals(dog.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(dog.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(dog.getDifficulty(), 3);
    }
    
}
