package test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.*;

import models.Status.Animal_T;
import models.tamagotchi.*;

public class TamagotchiTest {
    @Test
    public void Creation() {
        
    }

    @Test
    public void CurrentTime() {
        assertEquals(new Date(System.currentTimeMillis()).toString(), new Animal(40, Animal_T.CAT).GetBirthDate().toString());
    }
}
