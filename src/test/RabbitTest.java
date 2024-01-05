package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import org.junit.*;

import controller.InGameController;
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

    @Test
    public void eatTest(){
        InGameController obs = new InGameController();
        Tamagotchi test = new Rabbit("Dodo", new Place(EPlace.KITCHEN), 1);
        test.setObserver(obs);
        test.setCurrentSatiety(50);
        int satiety = test.getCurrentSatiety();
        test.kitchenAction();
        assertFalse(satiety == test.getCurrentSatiety());
    }
    
}
