package models.tamagotchi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import javafx.scene.image.Image;
import models.Place;

public class Cat extends Animal {

    /**
     * init the cat image and call Animal constructor
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Cat(String _nameString, float _weight, Place place) {
        super(_nameString, _weight, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/cat.png"));
        } catch (FileNotFoundException e) {}
    }

    //note de A : je teste des trucs
    public Cat(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/cat.png"));
        } catch (FileNotFoundException e) {}
    }

    //modif par A : ajout difficulty
    public Cat(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety, int difficulty) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety, difficulty);
    }
    
}
