package models.tamagotchi;

import java.sql.Date;

import models.Place;
import models.Status.*;

public abstract class Tamagotchi {
    private final float MAX_HEALTH_POINTS = 100;
    private final float MAX_ENERGY = 100;
    
    protected String name;

    protected float currentHealth;
    protected float currentEnergy;

    protected PhysicalState state;
    protected Place currentPlace;

    protected Date birthDate;

    /**
     * 
     */
    public Tamagotchi(String nameString) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;

        this.state = PhysicalState.IN_SHAPE;
        this.birthDate = new Date(System.currentTimeMillis());
        
        this.name = nameString;
    }

    protected void addEnergy() {

    }

    protected void play() {

    }

    /**
     * 
     * @return
     */
    public Date getBirthDate() {
        return this.birthDate;
    }
}