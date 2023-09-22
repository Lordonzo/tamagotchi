package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.Status.*;
import models.tamagotchi.Cat;

public class AnimalsTest {
    @Test
    public void Creation() {
        assertEquals(PhysicalStatus.ALIVE, new Cat(40).getPhysicalStatus());
    }
}
