package models.tamagotchi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

import javafx.scene.image.Image;
import models.Place;

public class Cat extends Animal {

    /**
     * init the cat image and call Animal constructor
     * @param _nameString
     * @param place
     */
    public Cat(String _nameString, Place place) {
        super(_nameString, place,2,7);

        try {
            image = new Image(new FileInputStream("resources/tama_sprites/cat.png"));
        } catch (FileNotFoundException e) {}
    }

    public Cat(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety);
    }
    
}
