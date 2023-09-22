package models.tamagotchi;

import models.Status.MentalStatus;
import models.Status.PhysicalStatus;

public abstract class Animal extends Tamagotchi{
    private final int MAX_HEALTH_POINTS = 100;
    private final int MAX_ENERGY = 100;

    private int currentHealth;
    
    private int currentHunger;
    private int currentThirsty;
    private int currentWeight;
    private int currentEnergy;
    private PhysicalStatus physicalStatus;
    private MentalStatus mentalStatus;

    /**
     * @param _weight
     */
    public Animal(int _weight) {
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


    /* GETTERS */

    public PhysicalStatus getPhysicalStatus() {
        return this.physicalStatus;
    }

    public MentalStatus getMentalStatus() {
        return this.mentalStatus;
    }

    public int getMAX_HEALTH_POINTS() {
        return this.MAX_HEALTH_POINTS;
    }

    public int getMAX_ENERGY() {
        return this.MAX_ENERGY;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public int getCurrentHunger() {
        return this.currentHunger;
    }

    public int getCurrentThirsty() {
        return this.currentThirsty;
    }

    public int getCurrentWeight() {
        return this.currentWeight;
    }

    public int getCurrentEnergy() {
        return this.currentEnergy;
    }
}