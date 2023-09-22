package models.tamagotchi;

import models.Status.MentalStatus;
import models.Status.PhysicalStatus;

public abstract class Animal extends Tamagotchi{
    private float currentHunger;
    private float currentThirsty;
    private float currentWeight;
    private PhysicalStatus physicalStatus;
    private MentalStatus mentalStatus;

    /**
     * @param _weight
     */
    public Animal(float _weight) {
        super();
        this.currentHunger = 20;
        this.currentThirsty = 0;
        this.currentWeight = _weight;
        this.physicalStatus = PhysicalStatus.ALIVE;
        this.mentalStatus = MentalStatus.HAPPY;
    }

    /**
     * 
     */
    public void Eat() {
        if (this.currentHunger - 40 < 0) this.currentHunger = 0;
        else this.currentHunger -= 40;
    }

    /**
     * 
     */
    public void Drink() {
        if (this.currentThirsty - 40 < 0) this.currentThirsty = 0;
        else this.currentThirsty -= 40;
    }
}