package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListener;

import static org.junit.Assert.assertFalse;

import org.junit.*;

import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class RobotTest {

    protected PropertyChangeListener observer;

    @Test
    public void creationRobot() {
        Tamagotchi robot = new Robot("Sam", new Place(EPlace.LIVINGROOM), 3);
        assertEquals(robot.getName(),"Sam");
        assertEquals(robot.getCurrentPlace().getCurrentPlace(),EPlace.LIVINGROOM);
        assertEquals(robot.getDifficulty(), 3);
    }
    
    @Test
    public void chargeRobot() {
        Tamagotchi robot = new Robot("BipBoup", new Place(EPlace.BEDROOM),1);
        robot.setBedroomActionRunning(true);
        assertTrue(robot.getBedroomActionRunning().get());
        robot.setBedroomActionRunning(false);
        assertTrue(robot.getBedroomActionStop().get());
    }

    @Test
    public void cleaningRobot() {
        Tamagotchi robot = new Robot("BipBoup", new Place(EPlace.TOILET), 1);
        robot.setCurrentCleanliness(50);
        int memory = robot.getCurrentCleaning();
        robot.toiletAction();
        assertFalse(memory == robot.getCurrentCleaning());
    }
}
