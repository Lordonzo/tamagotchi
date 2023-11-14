package models.tamagotchi;

import models.Status.Animal_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private final float MAX_SATIETY = 100;

    private float currentSatiety;
    private float currentWeight;
    private MentalState mentalState;
    private Animal_T animalType;

    /**
     * @param _weight
     */
    public Animal(String nameString, float weight, Animal_T animalType) {
        super(nameString);
        this.currentSatiety = MAX_SATIETY;
        this.currentWeight = weight;
        this.mentalState = MentalState.HAPPY;
        this.animalType = animalType;
    }

    /**
     * 
     */
    public void Eat() {}
    
}