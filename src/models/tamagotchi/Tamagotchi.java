package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.Timestamp;
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
    protected int difficulty;

    //Gain
    protected final int healthGain = 10;
    protected final int energyGain = 15;
    protected final int cleaningGain = 10;
    protected final int mentalGain = 20;
    protected final int satietyGain = 15;

    
    protected int id;
    protected String name;

    protected int currentHealth;
    protected int currentEnergy; // BATTERY ROBOT
    protected int currentSatiety; // MEMORY ROBOT

    protected Place currentPlace;

    protected LocalDateTime dateBirth;

    
    protected int currentBattery;
    protected int currentMemory;
    protected float currentWeight;
    protected int currentCleanliness;

    protected MentalState mentalState;
    protected int currentMental;

    protected int slot;


    protected Thread routine;
    protected Thread bedroomActionRoutine = null;
    protected final AtomicBoolean closeGame = new AtomicBoolean(false);
    protected final AtomicBoolean running = new AtomicBoolean(false);
    protected final AtomicBoolean bedroomActionRunning = new AtomicBoolean(false);
    protected final AtomicBoolean bedroomActionStop = new AtomicBoolean(true);

    //To call some event after x loop
    protected int cnt;
    protected int gardenActionCd= 0;
    protected final int weatherCnt = 6;
    protected final int maxCnt = 10;

    protected PropertyChangeListener observer;
    protected Image image;

    protected final boolean DEBUG = false;



    


    //TODO commenter les getters setters
    /**
     * 
     */
    public Tamagotchi(String _nameString, Place place,int difficulty) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;
        this.currentCleanliness = MAX_CLEAN;
        this.currentMental = MAX_MENTAL;
        this.currentSatiety = MAX_SATIETY;
        this.mentalState = MentalState.HAPPY;
        this.name = _nameString;
        this.currentPlace = place;
        this.difficulty = difficulty;
        dateBirth = LocalDateTime.now();
        //Weather random
        currentPlace.setWeather(Weather.values()[new Random().nextInt(5)]);
        setDifficulty(difficulty);

        
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
    public Tamagotchi(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int difficulty) {
        this.id = id; // 1
        this.currentHealth = currentHealth; // 5
        this.currentEnergy = currentEnergy; // 6
        this.currentCleanliness = currentCleanliness; // 9
        this.name = nameString; // 2
        this.currentWeight = currentWeight; // 8
        this.currentPlace = place; // 12
        this.slot = slotSaved; // 13
        this.dateBirth = birDateTime; // 3
        this.currentMental = mentalState; // 11
        this.difficulty = difficulty; // 14
        setDifficulty(difficulty);
        updateMentalState();
    }

    //pour le robot
    //ajouté par A
    public Tamagotchi(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentBattery, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentMemory, int difficulty) {
        this.id = id; // 1
        this.currentHealth = currentHealth; // 5
        this.currentBattery = currentBattery; // 6, energy=battery
        this.currentCleanliness = currentCleanliness; // 9
        this.name = nameString; // 2
        this.currentWeight = currentWeight; // 8
        this.currentPlace = place; // 12
        this.slot = slotSaved; // 13
        this.dateBirth = birDateTime; // 3
        this.currentMental = mentalState; // 11
        this.currentMemory = currentMemory; // 7, memory=satiety
        this.difficulty = difficulty; // 14
        setDifficulty(difficulty);
        updateMentalState();
    }

    public int getNB_SEC(){
        return NB_SEC;
    }
    public int getSlot() {
        return slot;
    }

    /**
     * set the difficulty of the game
     * 1 = easy
     * 2 = normal
     * 56 = godmode for debug
     * 66 = mental testing
     * 3 or anything else is hard
     * @param _difficulty
     */
    public void setDifficulty(int _difficulty){
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
        else if(_difficulty == 66){
            cleaningDifficulty = 20;
            mentalDifficulty = 100;
            energyDifficulty = 20;
            satietyDifficulty = 20;
        }
        else{
            cleaningDifficulty = 5;
            mentalDifficulty = 10;
            energyDifficulty = 7;
            satietyDifficulty = 9;
        }   
    }

    public int getDifficulty() {
        return difficulty;
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getDateBirth() {
        return dateBirth;
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
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
    public int getCurrentMental() {
        return this.currentMental;
    }
    public Place getCurrentPlace() {
        return this.currentPlace;
    }
    public int getCurrentCleaning() {
        return this.currentCleanliness;
    }
    public void setCurrentCleanliness(int currentCleanliness) {
        this.currentCleanliness = currentCleanliness;
    }
    public void setMentalState(MentalState mentalState) {
        this.mentalState = mentalState;
    }
    public MentalState getMentalState() {
        return mentalState;
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

    protected Tamagotchi getTamagotchi(){
        return this;
    }
    //_______________________________________

    public AtomicBoolean getBedroomActionRunning() {
        return bedroomActionRunning;
    }
    /**
     * set bedroomActionRunning
     * @param _boolean
     */
    public void setBedroomActionRunning(boolean _boolean){
        bedroomActionRunning.set(_boolean);
    }

    
    //_________________________________________

    /**
     * set closeGame
     * used to kill bedroomActionRoutine and the main routine when the game is closed
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
        closeGame.set(true);
        currentHealth = 0;
        observer.propertyChange(new PropertyChangeEvent(this, "die", null, _cause));
    }



    //public void decreaseHealth(int _satietyLost,int _cleaningLost,int _energyLost){}//TODO changer car pas la meme pour le robot

    protected void increaseHealth(){
        if(currentEnergy > 80){
            healthInc();
        }
        if(currentCleanliness > 80){
            healthInc();
        }
    }//TODO

    /**
     * increase currentHealth by healthGain
     * check if the result is  <100
     */
    protected void healthInc(){
        if(currentHealth+healthGain >=100){
            currentHealth = 100;
            return;
        }
        else currentHealth+=healthGain;
    }
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
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeather", null,null));
    }


    /**
     * when it's ranning and the tamagotchi is outside, it takes damages and lowers is mental
     * @param _damage
     */
    public void ranningEvent(int _damage,int _mental){
        if((Place.getWeather().equals(Weather.RAINY)|| (Place.getWeather().equals(Weather.THUNDER))) && currentPlace.getCurrentPlace().equals(EPlace.GARDEN)){
            currentHealth-=_damage;
            currentMental-=_mental;
        }
            
    }

    public void kitchenAction(){}

    /**
     * increase currentCleanliness
     */
    public void toiletAction(){
        if(currentCleanliness+cleaningGain <=100){
            currentCleanliness+=cleaningGain;
        }
        else{ currentCleanliness =100;
        }    
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, currentCleanliness));
    }

    public void gardenAction(){
        if(gardenActionCd == 0){
            observer.propertyChange(new PropertyChangeEvent(this, "gardenActionPrep", null, mentalState));
            if(mentalGain+currentMental > 100)currentMental = 100;
            else currentMental+=mentalGain;
            updateMentalState();
            observer.propertyChange(new PropertyChangeEvent(this, "UpdateMental", null, mentalState));
            gardenActionCd = new Random().nextInt(4,8);
        }
        else{
            observer.propertyChange(new PropertyChangeEvent(this, "no", null, null));
        }
    }

    public void livingroomAction(){
        observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "livingroomActionPrep", null, currentEnergy));
    }


    static  int k = 0;
    public void initBedroomActionRoutine(){
        try {
        bedroomActionRoutine = new Thread(){
                public void run() {
                    try {
                        while(!closeGame.get()){
                            int i = 0;
                            if(!bedroomActionRunning.get() && !bedroomActionStop.get()){
                                stopRoutine();
                                routine.join();
                                System.out.println("ISALIVED routine : " + routine.isAlive());
                                /*while(i < 5000){
                                    Thread.sleep(100);
                                    i+=100;
                                    //if actionButton is pressed
                                    if(bedroomActionStop.get()) break;
                                }*/
                                //if actionButton is pressed
                                if(!bedroomActionStop.get()){ //TODO trouver une autre solution car si le mec est assez rapide il peut cancel et recancel
                                    currentEnergyIncrease(); //TODO changer le nom
                                }
                            }
                            //if the button has been pressed again
                            if(bedroomActionRunning.get() && bedroomActionStop.get()){
                                System.out.println("FIN");
                                
                            }
                        }
                        }
                    catch (Exception e) {
                                // TODO: handle exception
                                }    
                }
                public void currentEnergyIncrease(){
                    try {
                        observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "bedroomActionPrep", null, currentEnergy));
                        bedroomActionRunning.set(true);
                        int i = 0;
                        k++;
                        System.out.println(k);
                        while(currentEnergy < 100 && !closeGame.get() && !bedroomActionStop.get()){
                            currentEnergy+=energyGain;
                            System.out.println("CURRENT ENERGY : " + currentEnergy);
                            observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "updateStat2", null, currentEnergy));
                            if(currentEnergy > 100){
                                currentEnergy = 100;
                                break;
                            }
                            i = 0;
                                while(i < 5000){
                                    Thread.sleep(100);
                                    i+=100;
                                    //if actionButton is pressed
                                    if(bedroomActionStop.get()) break;
                                }
                        }
                        bedroomActionStop.set(true);
                        bedroomActionRunning.set(false);
                        observer.propertyChange(new PropertyChangeEvent(this, "enableButtons", null, null));
                        startRoutine();  
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            };
            bedroomActionRoutine.setDaemon(true);
            bedroomActionRoutine.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    public void bedroomAction(){
        if(bedroomActionRoutine == null){
            initBedroomActionRoutine();
        }
        bedroomActionStop.set(!bedroomActionStop.get());
    }

    public AtomicBoolean getBedroomActionStop() {
        return bedroomActionStop;
    }

    //TODO USELESS I THINK
    public Thread getBedroomActionRoutine() {
        return bedroomActionRoutine;
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
    public void setCurrentHealth(int _currentHealth){
        this.currentHealth = _currentHealth;
    }

    public void setCurrentWeight(float _currentWeight) {
        this.currentWeight = _currentWeight;
    }


    protected void updateMentalState() {
        int mental = getCurrentMental();
        if(mental > 80){
            this.setMentalState(MentalState.HAPPY);
        }
        else if(mental > 50){
            this.setMentalState(MentalState.JOLLY);
        }
        else if(mental > 20){
            this.setMentalState(MentalState.SAD);
        }
        else{
            this.setMentalState(MentalState.DEPRESSED);
        }
    }
    
    protected void save(){
        observer.propertyChange(new PropertyChangeEvent(this, "saveGame", null,null));
    }

    
}