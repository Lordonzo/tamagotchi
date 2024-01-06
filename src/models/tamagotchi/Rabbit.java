package models.tamagotchi;

import java.io.File;
import java.time.LocalDateTime;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
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

    public Rabbit(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged,int currentHealth, int currentEnergy, int currentCleanliness, int currentSatiety, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        super(id, nameString, birDateTime,lastTimeChanged, currentHealth, currentEnergy, currentCleanliness, currentSatiety, currentWeight, mentalState, place, slotSaved, difficulty);
        setImage("src/resources/tama_sprites/rabbit.png");
    }

    @Override
    public Media makeGardenSound() {
        return rabbitSound();
    }
    private Media rabbitSound(){
        try {
            Media sound = new Media(new File("src/resources/sound/goofy_ahh_rabbit.mp3").toURI().toString());
            return sound;
        } catch (MediaException e) {
            return null;
        }
    }
}
