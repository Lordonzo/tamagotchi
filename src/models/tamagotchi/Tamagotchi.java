package models.tamagotchi;

import java.sql.Date;

import models.Place;
import models.Status.*;
import java.util.Random;

public abstract class Tamagotchi {
    private final float MAX_HEALTH_POINTS = 100;
    private final float MAX_ENERGY = 100;
    
    private final int MAX_DAY = 30; // TODO valeurs a adapter
    private final int MIN_DAY = 7;
    
    protected int id;
    protected String name;

    protected float currentHealth;
    protected float currentEnergy;

    protected PhysicalState state;
    protected Place currentPlace;

    protected Date birthDate;
    protected Date deathDate;

    protected Tamagotchi_T type;
    protected float currentWeight;

    protected MentalState mentalState;



    //TODO commenter les getters setters
    //TODO faire une condition pour que le robot ne puisse pas mourir vieillesse
    /**
     * 
     */
    public Tamagotchi(String nameString,float currentWeight,Tamagotchi_T type) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;

        this.state = PhysicalState.IN_SHAPE;
        this.birthDate = new Date(System.currentTimeMillis());
        
        this.name = nameString;
        this.currentWeight = currentWeight;
        //Calcule dernier jour de l'animal(86400 = nombre de secondes dans un jour);
        this.deathDate = new Date(System.currentTimeMillis() + (86400*(new Random().nextInt(MIN_DAY,MAX_DAY))));
        this.type = type;
    }
    
    protected void addEnergy() {

    }

    protected void play() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCurrentEnergy() {
        return currentEnergy;
    }
    public Date getDeathDate() {
        return deathDate;
    }
    public PhysicalState getState() {
        return state;
    }
    public float getCurrentHealth() {
        return currentHealth;
    }
    public Tamagotchi_T getType() {
        return type;
    }
    public MentalState getMentalState() {
        return mentalState;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }
    public Place getCurrentPlace() {
        return this.currentPlace;
    }
}