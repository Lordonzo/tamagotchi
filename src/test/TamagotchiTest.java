package test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.*;

import models.tamagotchi.*;

public class TamagotchiTest {
    @Test
    public void Creation() {
        
    }

    @Test
    public void CurrentTime() {
        assertEquals(new Date(System.currentTimeMillis()).toString(), new Cat(40).GetBirthDate().toString());
    }
}
