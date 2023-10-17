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
    public Animal(float _weight, Animal_T _animalType) {
        super();
        this.currentSatiety = MAX_SATIETY;
        this.currentWeight = _weight;
        this.mentalState = MentalState.HAPPY;
        this.animalType = _animalType;
    }

    /**
     * 
     */
    public void TakeMeal() {}
}