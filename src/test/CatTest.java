package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class CatTest {
    @Test
    public void creationCat() {
        Tamagotchi cat = new Cat("Sam", new Place(EPlace.LIVINGROOM), 3);
        assertEquals(cat.getName(),"Sam");
        assertEquals(cat.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(cat.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(cat.getDifficulty(), 3);
    }
    

}
