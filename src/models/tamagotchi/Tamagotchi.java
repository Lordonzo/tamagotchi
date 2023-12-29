package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
    protected int healthDifficulty;
    protected int cleaningDifficulty;
    protected int mentalDifficulty;
    protected int energyDifficulty;
    protected int satietyDifficulty;
    protected final int rainDamage = 10;
    protected int overallDifficulty = 1;

    //Gain
    protected final int healthGain = 20;
    protected final int energyGain = 20;
    protected final int cleaningGain = 10;
    protected final int mentalGain = 20;
    protected final int satietyGain = 20;

    
    protected int id;
    protected String name;

    protected int currentHealth;
    protected int currentEnergy; // BATTERY ROBOT
    protected int currentSatiety; // MEMORY ROBOT

    protected PhysicalState state;
    protected Place currentPlace;

    protected LocalDateTime birthDate;

    
    protected float currentWeight;
    protected int currentCleanliness;

    protected MentalState mentalState;
    protected int currentMental;

    protected int slotTaken;


    protected Thread routine;
    protected Thread sleepRoutine;
    protected final AtomicBoolean closeGame = new AtomicBoolean(false);
    protected final AtomicBoolean running = new AtomicBoolean(false);
    protected final AtomicBoolean sleepRunning = new AtomicBoolean(false);

    //To call some event after x loop
    protected int cnt;

    protected PropertyChangeListener observer;
    protected Image image;

    protected final boolean DEBUG = false;

    protected final int weatherCnt = 6;
    protected final int maxCnt = 10;
    protected final int sleepCnt = 2;

    


    //TODO commenter les getters setters
    /**
     * 
     */
    public Tamagotchi(String _nameString, float _currentWeight, Place place) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;
        this.currentCleanliness = MAX_CLEAN;
        this.currentMental = MAX_MENTAL;
        this.currentSatiety = MAX_SATIETY;

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
    
    protected void play() {
        //TODO increase mental garden
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
            satietyDifficulty = 2;
            energyDifficulty = 2;
            mentalDifficulty = 5;
            healthDifficulty = 5;
        }
        else if(_difficulty == 2){
            cleaningDifficulty = 3;
            mentalDifficulty = 7;
            energyDifficulty = 4;
            satietyDifficulty = 6;
            
        }
        else if( _difficulty == 56){
            cleaningDifficulty = 0;
            mentalDifficulty = 0;
            energyDifficulty = 0;
            satietyDifficulty = 0;
        }
        else{
            cleaningDifficulty = 5;
            mentalDifficulty = 10;
            energyDifficulty = 7;
            satietyDifficulty = 9;
        }   
    }

    public int getOverallDifficulty() {
        return this.overallDifficulty;
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

    //THREAD__________________________________
    protected void initRoutine(){}
    public void stopRoutine(){
        running.set(false);
    }
    public void startRoutine(){
        running.set(true);
        initRoutine();
        routine.start();
    }
    /**
     * increase currentEnergy
     */
    //TODO arreter quand on quitte le jeu
    public void startSleep(){
        try {
            sleepRunning.set(true);
            stopRoutine();
            routine.join();
            sleepRoutine = new Thread(){
                public void run() {
                    while(currentEnergy < 100 && !closeGame.get() && sleepRunning.get()){
                        currentEnergy+=energyGain;
                        observer.propertyChange(new PropertyChangeEvent(this, "statsDisplay", null, null));
                        if(currentEnergy > 100){
                            currentEnergy = 100;
                            break;
                        }
                        try {
                            int i = 0;
                            while(i < 5000){
                                Thread.sleep(100);
                                i+=100;
                                //if actionButton is pressed
                                if(!sleepRunning.get()) break;
                            }
                        }
                        catch (Exception e) {
                            // TODO: handle exception
                            }
                    }
                }
            };
            sleepRoutine.setDaemon(true);
            sleepRoutine.start();
            //check if the sleep routine is finished and start the mainRoutine
            new Thread(){public void run(){
                try {
                    sleepRoutine.join();
                    observer.propertyChange(new PropertyChangeEvent(this, "enableButtons", null, null));
                    startRoutine();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                }
            }.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public AtomicBoolean getSleepRunning() {
        return sleepRunning;
    }
    /**
     * set sleepRunning
     * @param _boolean
     */
    public void setSleepRunning(boolean _boolean){
        sleepRunning.set(_boolean);
    }
    //_________________________________________

    /**
     * set closeGame
     * used to kill sleepRoutine and the main routine when the game is closed
     * @param _boolean
     */
    public void setCloseGame(boolean _boolean){
        closeGame.set(_boolean);
    }

    /**
     * kill the tamagotchi and print the cause of death
     * @param _cause
     */
    public void die(String _cause){
        System.out.println("L'animal est mort de : " +_cause);
        closeGame.set(true);
        observer.propertyChange(new PropertyChangeEvent(this, "die", null, null));
    }

    /**
     * decrease the mental,cleaning and energy stats
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     */
    public void decreaseStats(int _mental,int _cleaning, int _energy, int _satiety){
        if(currentEnergy-_energy < 0) currentEnergy = 0;
        else currentEnergy-=_energy;
        
        if(currentCleanliness-_cleaning < 0) currentCleanliness = 0;
        else currentCleanliness-=_cleaning;

        if(currentSatiety-_satiety < 0) currentSatiety = 0;
        else currentSatiety-=_satiety;

        if(mean()<50){
            if(currentMental-_mental < 0) {
                currentMental = 0;
                currentHealth = 0;
                die("Suicide");
            }
            else currentMental-=_mental;
        }

    }

    public void decreaseHealth(){}//TODO

    public void increaseHealth(){}//TODO
    /**
     * mean of stats
     * @return mean
     */
    public float mean(){
        return (currentCleanliness+currentHealth+currentEnergy)/3;
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


    /**
     * when it's ranning and the tamagotchi is outside, it takes damages and lowers is mental
     * @param _damage
     */
    public void ranningEvent(int _damage,int _mental){
        if(Place.getWeather().equals(Weather.RAINY) && currentPlace.getCurrentPlace().equals(EPlace.GARDEN)){
            currentHealth-=_damage;
            currentMental-=_mental;
        }
            
    }

    /**
     * increase currentSatiety
     */
    public void eat() {
        if(currentSatiety+satietyGain <=100){
            currentSatiety+=satietyGain;
        }
        else{ currentSatiety =100;
        }
        observer.propertyChange(new PropertyChangeEvent(this, "statsDisplay", null, null));
    }


    public int getCurrentSatiety() {
        return this.currentSatiety;
    }

    public void setCurrentSatiety(int _currentSatiety) {
        this.currentSatiety = _currentSatiety;
    }

    public float getCurrentWeight() {
        return this.currentWeight;
    }

    public void setCurrentWeight(float _currentWeight) {
        this.currentWeight = _currentWeight;
    }

    /**
     * increase currentCleanliness
     */
    public void clean(){
        if(currentCleanliness+cleaningGain <=100){
            currentCleanliness+=cleaningGain;
        }
        else{ currentCleanliness =100;
        }
        observer.propertyChange(new PropertyChangeEvent(this, "statsDisplay", null, null));
    }

    
}