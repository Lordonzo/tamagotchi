package models.tamagotchi;

import models.Status.Animal_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private float currentHunger;
    private float currentThirsty;
    private float currentWeight;
    private MentalState mentalState;
    private Animal_T animalType;

    /**
     * @param _weight
     */
    public Animal(float _weight, Animal_T _animalType) {
        super();
        this.currentHunger = 20;
        this.currentThirsty = 0;
        this.currentWeight = _weight;
        this.mentalState = MentalState.HAPPY;
        this.animalType = _animalType;
    }

    /**
     * 
     */
    public void Eat() {}

    /**
     * 
     */
    public void Drink() {}
}