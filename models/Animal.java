package models;

public abstract class Animal {
    private final int MAX_HEALTH_POINTS = 100;
    private final int MAX_ENERGY = 100;
    private int currentHealth;
    private int currentHunger;
    private int currentThirsty;
    private int currentWeight;
    private int currentEnergy;

    /**
     * 
     */
    public Animal(int _weight) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentHunger = 60;
        this.currentThirsty = 0;
        this.currentWeight = _weight;
        this.currentEnergy = MAX_ENERGY;
    }

    public void Eat() {
        if (this.currentHunger - 40 < 0) this.currentHunger = 0;
        else this.currentHunger -= 40;
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