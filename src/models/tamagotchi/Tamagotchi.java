package models.tamagotchi;

import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.Random;

import javafx.scene.image.Image;
import models.Place;
import models.Status.*;

public abstract class Tamagotchi {
    protected final int MAX_HEALTH_POINTS = 100;
    protected final int MAX_ENERGY = 100;
    protected final int MAX_CLEAN = 100;
    protected final int MAX_SATIETY = 100;
    protected final int MAX_MENTAL = 100;
    protected final int NB_SEC = 1000;

    //Difficulty
    protected int cleaningDifficulty;
    protected int mentalDifficulty;
    protected int energyDifficulty;
    
    protected int id;
    protected String name;

    protected int currentHealth;
    protected int currentEnergy; // BATTERY ROBOT

    protected PhysicalState state;
    protected Place currentPlace;

    protected LocalDateTime birthDate;

    protected float currentWeight;
    protected int currentCleanliness;

    protected MentalState mentalState;
    protected int currentMental;

    protected int slotTaken;


    protected Thread routine;
    protected boolean exit;
    //To call some event x routine call
    protected int cnt;

    protected PropertyChangeListener observer;
    protected Image image;

    protected final boolean DEBUG = true;

    protected final int weatherCnt = 6;
    protected final int maxCnt = 10;



    //TODO commenter les getters setters
    //TODO faire une condition pour que le robot ne puisse pas mourir vieillesse
    /**
     * 
     */
    public Tamagotchi(String _nameString, float _currentWeight, Place place) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;
        this.currentCleanliness = MAX_CLEAN;
        this.currentMental = MAX_MENTAL;
        this.exit = false;
        this.state = PhysicalState.IN_SHAPE;
        
        this.name = _nameString;
        this.currentWeight = _currentWeight;
        this.currentPlace = place;
        //Weather random
        weatherHandle();
    }

    /**
     * 
     * @param nameString
     * @param currentWeight
     * @param currentHealth
     * @param currentEnergy
     * @param currentCleanliness
     * @param state
     * @param place
     */
    public Tamagotchi(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved) {
        this.id = id; // 1
        this.currentHealth = currentHealth; // 5
        this.currentEnergy = currentEnergy; // 6
        this.currentCleanliness = currentCleanliness; // 9
        this.name = nameString; // 2
        this.currentWeight = currentWeight; // 8
        this.currentPlace = place; // 12
        this.slotTaken = slotSaved; // 13
        this.birthDate = birDateTime; // 3
        this.currentMental = mentalState; // 11
    }
    
    protected void addEnergy() {

    }

    protected void play() {

    }

    public int getNB_SEC(){
        return NB_SEC;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
    public int getMentalState() {
        return this.currentMental;
    }
    public Place getCurrentPlace() {
        return this.currentPlace;
    }
    public int getCurrentCleaning() {
        return this.currentCleanliness;
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
        
        if(currentCleanliness-_cleaning < 0) currentCleanliness = 0;
        else currentCleanliness-=_cleaning;

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
        return (currentCleanliness+currentHealth)/2;
    }

    public void setObserver(PropertyChangeListener _ob){
        this.observer = _ob;
    }

    public void goToRightPlace(){
        this.currentPlace = currentPlace.getNextPlace();
    }
    public void goToLeftPlace(){
        this.currentPlace = currentPlace.getPreviousPlace();
    }

    /**
     * Setting weather with equal random chances
     */
    public void weatherHandle(){
        currentPlace.setWeather(Weather.values()[new Random().nextInt(5)]);
    }
}