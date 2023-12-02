package test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.*;

import models.Status.Tamagotchi_T;
import models.tamagotchi.*;

public class TamagotchiTest {
    @Test
    public void creation() {
        
    }

    @Test
    public void currentTime() {
        assertEquals(new Date(System.currentTimeMillis()).toString(), new Animal("Filousse", 40, Tamagotchi_T.CAT).getBirthDate().toString());
    }
}
