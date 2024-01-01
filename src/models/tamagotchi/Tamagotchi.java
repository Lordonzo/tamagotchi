package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    protected final int BEDROOM_WAIT = 5000;

    //Difficulty
    protected int healthDifficulty;
    protected int cleaningDifficulty;
    protected int mentalDifficulty;
    protected int energyDifficulty;
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
    protected int currentEnergy;
    protected int currentSatiety;

    protected Place currentPlace;

    protected LocalDateTime dateBirth;
    protected LocalDateTime lastTimeChanged;
    
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

    public String stat1String;
    public String stat2String;
    public String stat3String;
    public String stat4String;
    public String bedRoomActionString;
    public String bedRoomActionStopString;
    public String kitchenActionString;
    public String gardenActionString;
    public String toiletActionString;
    public String livingroomActionString;



    


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
        this.dateBirth = LocalDateTime.now();
        this.lastTimeChanged = LocalDateTime.now();
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
    public Tamagotchi(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged, int currentHealth, int currentEnergy, int currentCleanliness, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        this.id = id; // 1
        this.name = nameString; // 2
        this.dateBirth = birDateTime; // 3
        this.lastTimeChanged = lastTimeChanged; // 4
        this.currentHealth = currentHealth; // 5
        this.currentEnergy = currentEnergy; // 6
        this.currentCleanliness = currentCleanliness; // 7
        this.currentWeight = currentWeight; // 9
        this.currentMental = mentalState; // 11
        this.currentPlace = place; // 12
        this.slot = slotSaved; // 13
        this.difficulty = difficulty; // 14
        setDifficulty(difficulty);
        updateMentalState();     
    }

    public void setUIString(String _stat1,String _stat2, String _stat3,String _stat4,String _bedroomAction, String _bedroomStop,String _kitchenAction,String _gardenAction,String _toiletAction, String _livingroomAction){
        stat1String = _stat1;
        stat2String = _stat2;
        stat3String = _stat3;
        stat4String = _stat4;
        bedRoomActionString = _bedroomAction;
        bedRoomActionStopString = _bedroomStop;
        kitchenActionString = _kitchenAction;
        gardenActionString = _gardenAction;
        toiletActionString = _toiletAction;
        livingroomActionString = _livingroomAction;

    }

    public Image getImage() {
        return image;
    }
    public void setImage(String _image) {
        try {
            this.image = new Image(new FileInputStream(_image));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            energyDifficulty = 2;
            mentalDifficulty = 5;
            healthDifficulty = 5;
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
        else if(_difficulty == 66){
            cleaningDifficulty = 20;
            mentalDifficulty = 100;
            energyDifficulty = 20;
        }
        else{
            cleaningDifficulty = 5;
            mentalDifficulty = 10;
            energyDifficulty = 7;
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
     * check if the result is  <MAX_HEALTH_POINTS
     */
    protected void healthInc(){
        if(currentHealth+healthGain >=MAX_HEALTH_POINTS){
            currentHealth = MAX_HEALTH_POINTS;
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

    /**
     * perform the kitchen action
     * override in sub classes
     */
    public void kitchenAction(){}

    /**
     * perfom the toilet action
     * increase currentCleanliness
     */
    public void toiletAction(){
        if(currentCleanliness+cleaningGain <=MAX_CLEAN){
            currentCleanliness+=cleaningGain;
        }
        else{ currentCleanliness =MAX_CLEAN;
        }    
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, currentCleanliness));
    }

    /**
     * perform the garden action
     * increase currentMental
     */
    public void gardenAction(){
        if(gardenActionCd == 0){
            observer.propertyChange(new PropertyChangeEvent(this, "gardenActionPrep", null, mentalState));
            if(mentalGain+currentMental > MAX_MENTAL)currentMental = MAX_MENTAL;
            else currentMental+=mentalGain;
            updateMentalState();
            observer.propertyChange(new PropertyChangeEvent(this, "UpdateMental", null, mentalState));
            gardenActionCd = new Random().nextInt(4,8);
        }
        else{
            observer.propertyChange(new PropertyChangeEvent(this, "no", null, null));
        }
    }

    /**
     * perform livingroom action
     * call observer to do an backflip animation on the tamagotchi's sprite
     */
    public void livingroomAction(){
        observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "livingroomActionPrep", null, currentEnergy));
    }



    /**
     * init bedroom action routine
     * used in bedroomAction
     */
    public void initBedroomActionRoutine(){
        try {
        bedroomActionRoutine = new Thread(){
                public void run() {
                    try {
                        while(!closeGame.get()){
                            //if the method currentEnergy is finished
                            if(!bedroomActionRunning.get() && !bedroomActionStop.get()){
                                stopRoutine();
                                routine.join();
                                currentEnergyIncrease(); 
                            }
                        }
                        }
                    catch (Exception e) {
                                // TODO: handle exception
                                }    
                }

                /**
                 * 
                 */
                public void currentEnergyIncrease(){
                    try {
                        bedroomActionRunning.set(true);
                        observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "bedroomActionPrep", null, currentEnergy));
                        
                        //wait
                        int i = 0;
                        while(i < BEDROOM_WAIT){
                                Thread.sleep(100);
                                i+=100;
                                //if actionButton is pressed
                                if(bedroomActionStop.get()) break;
                        }

                        //increase currentEnergy until it's full or the game is closed or the actionButton has been pressed
                        while(currentEnergy < MAX_ENERGY && !closeGame.get() && !bedroomActionStop.get()){
                            currentEnergy+=energyGain;
                            observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "updateStat2", null, currentEnergy));
                            if(currentEnergy > MAX_ENERGY){
                                currentEnergy = MAX_ENERGY;
                                break; // quit if currentEnergy is full
                            }

                            i = 0;
                            while(i < BEDROOM_WAIT){
                                Thread.sleep(100);
                                i+=100;
                                //if actionButton is pressed
                                if(bedroomActionStop.get()) break;
                                }
                        }
                        bedroomActionStop.set(true);
                        bedroomActionRunning.set(false);
                        observer.propertyChange(new PropertyChangeEvent(this, "enableButtons", null, null));
                        observer.propertyChange(new PropertyChangeEvent(this, "updatePlaceText", null, null));
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

    /**
     * init bedroomActionRoutine on the first call
     * reverse bedroomActionStop value on the other 
     */
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