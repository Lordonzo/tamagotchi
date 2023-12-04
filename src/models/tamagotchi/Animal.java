package models.tamagotchi;

import models.Status.Tamagotchi_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private final float MAX_SATIETY = 100;

    private float currentSatiety;
    private float currentWeight;

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
    

    public float getMAX_SATIETY() {
        return this.MAX_SATIETY;
    }


    public float getCurrentSatiety() {
        return this.currentSatiety;
    }

    public void setCurrentSatiety(float currentSatiety) {
        this.currentSatiety = currentSatiety;
    }

    public float getCurrentWeight() {
        return this.currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }
}