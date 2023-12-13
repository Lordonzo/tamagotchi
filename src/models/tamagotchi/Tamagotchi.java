package models.tamagotchi;

import java.sql.Date;

import models.Place;
import models.Status.*;
import java.util.Random;

public abstract class Tamagotchi {
    private final int MAX_HEALTH_POINTS = 100;
    private final int MAX_ENERGY = 100;
    
    private final int MAX_DAY = 30; // TODO valeurs a adapter
    private final int MIN_DAY = 7;

    protected final int NB_SEC = 3;

    //Difficulty
    protected int cleaningDifficulty;
    protected int mentalDifficulty;
    protected int energyDifficulty;
    
    protected int id;
    protected String name;

    protected int currentHealth;
    protected int currentEnergy;

    protected PhysicalState state;
    protected Place currentPlace;

    protected Date birthDate;
    protected Date deathDate;

    protected Tamagotchi_T type;
    protected float currentWeight;
    
    protected int currentCleaning;

    protected MentalState mentalState;
    protected int currentMental;

    protected Thread routine;
    protected boolean exit;


    protected final boolean DEBUG = true;



    //TODO commenter les getters setters
    //TODO faire une condition pour que le robot ne puisse pas mourir vieillesse
    /**
     * 
     */
    public Tamagotchi(String _nameString,float _currentWeight,Tamagotchi_T _type,int _difficulty) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;

        this.state = PhysicalState.IN_SHAPE;
        this.birthDate = new Date(System.currentTimeMillis());
        
        this.name = _nameString;
        this.currentWeight = _currentWeight;
        //Calcule dernier jour de l'animal(86400 = nombre de secondes dans un jour);
        this.deathDate = new Date(System.currentTimeMillis() + (86400*(new Random().nextInt(MIN_DAY,MAX_DAY))));
        this.type = _type;
    }
    
    protected void addEnergy() {

    }

    protected void play() {

    }


    /**
     * set the difficulty of the game
     * 1 = easy
     * 2 = normal
     * 56 = godmode for debug
     * 3 or anything else is hard
     * @param _difficulty
     */
    protected void setDifficulty(int _difficulty){
        if(_difficulty==1){
            cleaningDifficulty = 2;
            mentalDifficulty = 5;
            energyDifficulty = 2;
        }
        else if(_difficulty == 2){
            cleaningDifficulty = 3;
            mentalDifficulty = 7;
            energyDifficulty = 4;
        }
        else if( _difficulty == 56){
            cleaningDifficulty = 0;
            mentalDifficulty = 0;
            energyDifficulty = 0;
        }
        else{
            cleaningDifficulty = 5;
            mentalDifficulty = 10;
            energyDifficulty = 7;
        }   
    }
    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set Tamagotchi's name
     * @param name
     */
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
    public void start(){
        routine.start();
    }
    public void stop(){
        exit = true;
    }

    /**
     * decrease the mental,cleanig and energy stat
     * @param _mental
     * @param _cleaning
     * @param _energy
     */
    public void decreaseStats(int _mental,int _cleaning, int _energy){
        if(currentEnergy-_energy < 0) currentEnergy = 0;
        else currentEnergy-=_energy;
        
        if(currentCleaning-_cleaning < 0) currentCleaning = 0;
        else currentCleaning-=_cleaning;

        if(currentMental-_mental < 0) currentMental = 0;
        else currentMental-=_mental;
    }
}