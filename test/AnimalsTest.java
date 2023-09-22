package test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.*;
import models.tamagotchi.*;

public class AnimalsTest {
    @Test
    public void Creation() {
        assertEquals(100.0, new Cat(40).GetMaxHealth(), 0.1);
    }
}
