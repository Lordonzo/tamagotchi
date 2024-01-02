package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;

import javafx.scene.media.Media;
import models.Place;
import models.Status.MentalState;

public abstract class Animal extends Tamagotchi {

    private int sleepCd = 0;
    protected int satietyDifficulty;
    protected int currentSatiety;
    protected final int SATIETY_GAIN = 10;
    protected final int MAX_SATIETY = 100;


    /**
     * init the animal and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
   public Animal(String _nameString, Place _place, int _difficulty) {
        super(_nameString, _place,_difficulty);
        this.currentSatiety = MAX_SATIETY;
        setUIString("health","energy","cleanliness","satiety","sleep","wakeup","eat","play","clean","yipee");
    }

    protected Animal(String _nameString,Place _place,int _difficulty,float MIN_STARTING_WEIGHT,float MAX_STARTING_WEIGHT){
        this(_nameString,_place,_difficulty);
        //setcurrentweight and round it to two digit
        float random = Math.round(new Random().nextFloat(MIN_STARTING_WEIGHT,MAX_STARTING_WEIGHT) *100);
        random /=100;
        setCurrentWeight(random);
        setUIString("health","energy","cleanliness","satiety","sleep","wakeup","eat","play","clean","yipee");
    }

    /*//note de A : je teste des choses
    public Animal(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        this.mentalState = MentalState.HAPPY;
    }*/

    //modif par A : ajout difficulty
    public Animal(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged, int currentHealth, int currentEnergy,int currentCleanliness, int currentSatiety, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        super(id, nameString, birDateTime,lastTimeChanged, currentHealth, currentEnergy, currentCleanliness, currentWeight, mentalState, place, slotSaved, difficulty);
        this.currentSatiety = currentSatiety; // 8
        setUIString("health","energy","cleanliness","satiety","sleep","wakeup","eat","play","clean","yipee");
   
    }


    /**
     * decrease the mental,cleaning and energy stats
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     */
    @Override
    protected void updateStats(int _mental,int _cleaning, int _energy, int _satiety){
        if(currentEnergy-_energy < 0) currentEnergy = 0;
        else currentEnergy-=_energy;
        
        if(currentCleanliness-_cleaning < 0) currentCleanliness = 0;
        else currentCleanliness-=_cleaning;

        if(currentSatiety-_satiety < 0)currentSatiety = 0;
        else currentSatiety-=_satiety;

        if(mean()<50){
            if(currentMental-_mental < 0) {
                currentHealth = 0;
                currentMental = 0;
                die("Suicide");
            }
            else currentMental-=_mental;
        }
        //Damages
        if(currentHealth !=0){
            int satiety = 0;
            int clean = 0;
            int energy = 0;
            if(currentSatiety < 20){
                if(currentSatiety < 10) satiety = 10;
                else satiety = 5;
            }
            if(currentCleanliness < 20){
                if(currentCleanliness < 10) clean=10;
                else clean = 5;
            }
            if(energy < 20){
                if(currentEnergy < 10) energy= 10;
                else energy = 5;
            }
            decreaseHealth(satiety,clean,energy);
        }

    }

    /**
     * decrease the animal health if the conditions are met
     * kill the animal if health <= 0
     * @param _satietyLost
     * @param _cleaningLost
     * @param _energyLost
     */
    protected void decreaseHealth(int _satietyLost, int _cleaningLost, int _energyLost){
        if(currentSatiety <= 0){
            if(currentHealth-_satietyLost < 0) currentHealth = 0;
            else currentHealth-=_satietyLost;
        }
        if(currentCleanliness <= 0){
            if(currentHealth-_cleaningLost < 0) currentHealth = 0;
            else currentHealth-=_cleaningLost;
        }
        if(currentEnergy <= 0){
            if(currentHealth-_energyLost < 0) currentHealth = 0;
            else currentHealth-=_energyLost;
        }
        if(currentHealth <= 0){
            die("Mistreatement");
        }
    }


    
    public int getCurrentSatiety() {
        return this.currentSatiety;
    }

    public void setCurrentSatiety(int _currentSatiety) {
        this.currentSatiety = _currentSatiety;
    }

    /**
     * 
     * @return mean
     */
    @Override
    public float mean(){
        return (currentCleanliness+currentHealth+currentSatiety)/3;
    }

    /**
     * call to tamagotchi setdifficulty
     * set satiety difficulty
     */
    @Override
    public void setDifficulty(int _difficulty) {
        // TODO Auto-generated method stub
        super.setDifficulty(_difficulty);
        if(_difficulty == 1){
            this.satietyDifficulty = 2;
        }
        else if(_difficulty == 2){
            this.satietyDifficulty = 6;
        }
        else if(_difficulty == 56){
            this.satietyDifficulty = 0;
        }
        else if(_difficulty == 66){
            satietyDifficulty = 20;
        }
        else{
            satietyDifficulty = 9;
        }
    }


    @Override
    protected void initRoutine(){
        routine = new Thread(){
            public void run() {
                try{
                    //last Connexion stats deacrease
                    updateStats((timePassed*mentalDifficulty)/10, (timePassed*cleaningDifficulty)/10, (timePassed*energyDifficulty)/10, (timePassed*satietyDifficulty)/10);
                    //Calling observer
                        updateAllStats();
                    do{
                        //timeout___________________
                        int i = 0;
                        while(i < NB_SEC){
                            i+=100;
                            sleep(100);
                            //stop the timeout
                            if(!running.get() || closeGame.get()) break;
                        }
                        //stop the routine
                        if(!running.get() || closeGame.get()) break;
                        //__________________________

                        //statUpdate
                        updateStats(mentalDifficulty, cleaningDifficulty, energyDifficulty,satietyDifficulty);
                        
                        //DEBUG_________________________________________________________________
                        if(DEBUG){
                            System.out.println("mean : " + mean());
                            System.out.println("currentCleaning :"+currentCleanliness);
                            System.out.println("currentSatiety :"+currentSatiety);
                            System.out.println("currentEnergy"+currentEnergy);
                            System.out.println("currentHealth :"+currentHealth);
                            System.out.println("currentMental:"+currentMental);
                            System.out.println("Weather :" + currentPlace.getWeather().toString());
                            System.out.println("Counter : "+cnt);
                        }
                        //______________________________________________________________________
                        //Weather_____________________
                        if(cnt == weatherCnt){
                            weatherHandle();
                        }
                        cnt++;
                        if(cnt > maxCnt){
                            cnt = 0;
                        }
                        //____________________________

                        //Electrocution___________________________
                        ranningEvent(rainDamage,mentalDifficulty);
                        //________________________________________


                        //looseWeight_____________________________
                        looseWeight();
                        //________________________________________


                        //increaseHealth__________________________
                        increaseHealth();
                        //________________________________________


                        //sleepCd_________________________________
                        if(sleepCd > 0){
                            sleepCd--;
                        }
                        //________________________________________
                        //MentalCanceled__________________________
                        if(gardenActionCd > 0){
                            gardenActionCd--;
                        }
                        //________________________________________
                        //update Mental State_____________________
                        updateMentalState();
                        //________________________________________


                        //Calling observer
                        updateAllStats();

                        //save____________________________________
                        save();
                        //________________________________________

                    } while(running.get() && !closeGame.get());
                
                    
                }
                catch(Exception e){
                    //TODO routine d'erreur
                    System.err.println("Thread error : "+e.getMessage());
                }
            }
        };
        //stop the routine when the user kill the program with the X button
        routine.setDaemon(true);
    }
    public int getSleepCd() {
        return sleepCd;
    }
    public void setSleepCd(int _sleepCd) {
        this.sleepCd = _sleepCd;
    }


    /**
     * perform the bedroom action
     */
    @Override
    public void bedroomAction(){
        if((this.getSleepCd() != 0 && !this.getBedroomActionRunning().get())){
            //don't start the sleep routine
            observer.propertyChange(new PropertyChangeEvent(getTamagotchi(), "no", null, currentEnergy));
        }
        else{
            setSleepCd(5);
            super.bedroomAction();
        }

    }

    
    @Override
    public void kitchenAction(){
        observer.propertyChange(new PropertyChangeEvent(this, "kitchenActionPrep", null,null));
        if(currentSatiety+SATIETY_GAIN <=MAX_SATIETY){
            currentSatiety+=SATIETY_GAIN;
        }
        else{
            currentSatiety =MAX_SATIETY;
            gainWeight();
        }
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null,currentSatiety));
    }



    protected void gainWeight(){
            setCurrentWeight(Math.round(currentWeight+(currentWeight/10)));
    }
    /**
     * the animal loose weight if currentSatiety < 20
     */
    protected void looseWeight(){
        if(currentSatiety < 20){      
            setCurrentWeight(Math.round(currentWeight-(currentWeight/10)));
        }
    }


    /**
     * increase health
     */
    @Override
    protected void increaseHealth(){
        super.increaseHealth();
        if(currentSatiety > 80){
            healthInc();
        }
    }

    @Override
    public Media makeGardenSound() {
        return super.makeGardenSound();
    }

    @Override
    public Media makeKitchenSound() {
        return animalKitchenSound();
    }
    private Media animalKitchenSound(){
        return new Media(new File("src/resources/sound/goofy_ahh_eating.mp3").toURI().toString());
    }
    @Override
    public Media makeLivingroomSound(){
        return animalLivingroomSound();
    }
    private Media animalLivingroomSound(){
        return new Media(new File("src/resources/sound/goofy_ahh_backflipping.mp3").toURI().toString());
    }
    @Override
    public Media makeStopBedroomSound() {
        return stopBedroomsound();
    }
    private Media stopBedroomsound() {
        return new Media(new File("src/resources/sound/goofy_ahh_sleeping_stop.mp3").toURI().toString());
    }

    /**
     * call the observer to visually update the stats
     */
    protected void updateAllStats(){
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat1", null, getCurrentHealth()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat2", null, getCurrentEnergy()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, getCurrentCleaning()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null, getCurrentSatiety()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateMental", null, getMentalState()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeight", null, getCurrentWeight()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeather", null,null));

    }



}


