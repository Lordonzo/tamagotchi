package models.tamagotchi;

import java.time.LocalDateTime;

import models.Place;
import models.Status.*;

public abstract class Tamagotchi {
    private final int MAX_HEALTH_POINTS = 100;
    private final int MAX_ENERGY = 100;
    private final int MAX_CLEAN = 100;
    private final int MAX_SATIETY = 100;

    protected final int NB_SEC = 1000;

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

    protected LocalDateTime birthDate;

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
    public Tamagotchi(String _nameString,float _currentWeight,Tamagotchi_T _type) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;
        this.currentCleaning = MAX_CLEAN;
        this.exit = false;
        this.state = PhysicalState.IN_SHAPE;
        
        this.name = _nameString;
        this.currentWeight = _currentWeight;
        this.type = _type;
        this.currentPlace = new Place(EPlace.LIVINGROOM);
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

    public int getCurrentEnergy() {
        return currentEnergy;
    }
    public PhysicalState getState() {
        return state;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public Tamagotchi_T getType() {
        return type;
    }
    public MentalState getMentalState() {
        return mentalState;
    }
    public Place getCurrentPlace() {
        return this.currentPlace;
    }
    public int getCurrentCleaning() {
        return this.currentCleaning;
    }
    public void start(){
        exit = false;
        routine.start();
    }
    public void stop(){
        exit = true;
    }

    /**
     * kill the tamagotchi and print the cause of the death
     * @param _cause
     */
    public void die(String _cause){
        System.out.println("L'animal est mort de : " +_cause);
        stop();
    }

    /**
     * decrease the mental,cleaning and energy stats
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     */
    public void decreaseStats(int _mental,int _cleaning, int _energy){
        if(currentEnergy-_energy < 0) currentEnergy = 0;
        else currentEnergy-=_energy;
        
        if(currentCleaning-_cleaning < 0) currentCleaning = 0;
        else currentCleaning-=_cleaning;

        if(mean()<50){
            if(currentMental-_mental < 0) {
                currentMental = 0;
                currentHealth = 0;
                die("Suicide");
            }
            else currentMental-=_mental;
        }

    }

    public void decreaseHealth(){}

    /**
     * mean of stats
     * @return mean
     */
    public float mean(){
        return (currentCleaning+currentHealth)/2;
    }
}