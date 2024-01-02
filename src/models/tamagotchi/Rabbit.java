package models.tamagotchi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import models.Place;

public class Rabbit extends Animal {
    /**
     * init the rabbit image and call Animal constructor
     * @param _nameString
     * @param place
     */
    public Rabbit(String _nameString, Place place,int _difficulty) {
        super(_nameString, place,_difficulty,4,8);
        setImage("src/resources/tama_sprites/rabbit.png");

    }

    /*//note de A : je teste des trucs
    public Rabbit(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/rabbit.png"));
        } catch (FileNotFoundException e) {}
    }*/
    
    //modif par A : ajout difficulty
    //int, String, LocalDateTime, int, int, int, float, int, int, Place, int
    public Rabbit(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged,int currentHealth, int currentEnergy, int currentCleanliness, int currentSatiety, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        super(id, nameString, birDateTime,lastTimeChanged, currentHealth, currentEnergy, currentCleanliness, currentSatiety, currentWeight, mentalState, place, slotSaved, difficulty);
        setImage("src/resources/tama_sprites/rabbit.png");
    }

    @Override
    public Media makeGardenSound() {
        return rabbitSound();
    }
    private Media rabbitSound(){
        return new Media(new File("src/resources/sound/goofy_ahh_rabbit.mp3").toURI().toString());
    }
}
