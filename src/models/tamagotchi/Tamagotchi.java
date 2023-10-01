package models.tamagotchi;

import java.sql.Date;

import models.Status.PhysicalState;;

public abstract class Tamagotchi {
    private final float MAX_HEALTH_POINTS = 100;
    private final float MAX_ENERGY = 100;

    protected float currentHealth;
    protected float currentEnergy;

    protected PhysicalState state;

    protected Date birthDate;

    /**
     * 
     */
    public Tamagotchi() {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;

        this.state = PhysicalState.ALIVE;
        this.birthDate = new Date(System.currentTimeMillis());
    }

    /**
     * 
     * @return
     */
    public Date GetBirthDate() {
        return this.birthDate;
    }
}