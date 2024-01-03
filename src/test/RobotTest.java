package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class RobotTest {
    @Test
    public void creationRobot() {
        Tamagotchi robot = new Robot("Sam", new Place(EPlace.LIVINGROOM), 3);
        assertEquals(robot.getName(),"Sam");
        assertEquals(robot.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(robot.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(robot.getDifficulty(), 3);
    }
    
}
