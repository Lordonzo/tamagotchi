package models.tamagotchi;

import models.Status.Tamagotchi_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private final float MAX_SATIETY = 100;

    private float currentSatiety;
    private float currentWeight;
    private Tamagotchi_T animalType;

    /**
     * @param _weight
     */
    public Animal(String nameString, float weight, Tamagotchi_T animalType) {
        super(nameString,weight,animalType);
        this.currentSatiety = MAX_SATIETY;
        this.mentalState = MentalState.HAPPY;
    }

    /**
     * 
     */
    public void eat() {}
    
}