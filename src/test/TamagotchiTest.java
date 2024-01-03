package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;

import org.junit.*;

import javafx.scene.image.Image;
import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class TamagotchiTest {
    @Test
    public void setRealImage_whenCat(){
        Image img = null;
        try {
           img = new Image(new FileInputStream("src/resources/tama_sprites/cat.png"));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM),1);
        test.setImage("src/resources/tama_sprites/cat.png");
        assertEquals( img, test.getImage());
    }
    @Test
    public void setFakeImage_whenCat(){
        Tamagotchi test = new Cat("Jordan", new Place(EPlace.BEDROOM),1);
        test.setImage("src/resources/tama_sprites/cat.png");
        assertThrows(FileNotFoundException.class, () -> {test.getImage();});
    }
}
