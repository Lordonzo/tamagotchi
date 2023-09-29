package models.tamagotchi;

import models.Status.MentalState;

public abstract class Animal extends Tamagotchi{
    private float currentHunger;
    private float currentThirsty;
    private float currentWeight;
    private MentalState mentalState;

    /**
     * @param _weight
     */
    public Animal(float _weight) {
        super();
        this.currentHunger = 20;
        this.currentThirsty = 0;
        this.currentWeight = _weight;
        this.mentalState = MentalState.HAPPY;
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