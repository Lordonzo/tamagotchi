package models.tamagotchi;

import java.io.File;
import java.time.LocalDateTime;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import models.Place;

public class Dog extends Animal {


    /**
     * init the dog image and call Animal constructor
     * @param _nameString
     * @param place
     */
    public Dog(String _nameString, Place place,int _difficulty) {
        super(_nameString, place,_difficulty,4,15);
        setImage("src/resources/tama_sprites/dog.png");
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
    public Dog(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged,int currentHealth, int currentEnergy, int currentCleanliness, int currentSatiety, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        super(id, nameString, birDateTime,lastTimeChanged, currentHealth, currentEnergy, currentCleanliness, currentSatiety, currentWeight, mentalState, place, slotSaved, difficulty);
        setImage("src/resources/tama_sprites/dog.png");
    }

    @Override
    public Media makeGardenSound() {
        return bark();
    }

    private Media bark(){
        try {
            Media sound = new Media(new File("src/resources/sound/goofy_ahh_bark.mp3").toURI().toString());
            return sound;
        } catch (MediaException e) {
            // TODO: handle exception

            return null;
        }

    }
}
