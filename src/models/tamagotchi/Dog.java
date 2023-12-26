package models.tamagotchi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import javafx.scene.image.Image;
import models.Place;

public class Dog extends Animal {


    /**
     * init the dog image and call Animal constructor
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Dog(String _nameString, float _weight, Place place) {
        super(_nameString, _weight, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/dog.png"));
        } catch (FileNotFoundException e) {}
    }

    //int, String, LocalDateTime, int, int, int, float, int, int, Place, int
    public Dog(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety);
    }
}
