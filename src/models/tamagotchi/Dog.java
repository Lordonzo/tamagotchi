package models.tamagotchi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

import javafx.scene.image.Image;
import models.Place;

public class Dog extends Animal {


    /**
     * init the dog image and call Animal constructor
     * @param _nameString
     * @param place
     */
    public Dog(String _nameString, Place place,int _difficulty) {
        super(_nameString, place,_difficulty,4,15);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/dog.png"));
        } catch (FileNotFoundException e) {}
    }

    /*//note de A : je teste des trucs
    public Dog(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/dog.png"));
        } catch (FileNotFoundException e) {}
    }*/

    //modif par A : ajout difficulty
    //int, String, LocalDateTime, int, int, int, float, int, int, Place, int
    public Dog(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety, int difficulty) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety, difficulty);
    }
}
