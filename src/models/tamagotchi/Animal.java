package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;
import java.util.Random;

import models.Place;
import models.Status.MentalState;

public abstract class Animal extends Tamagotchi {

    private int sleepCancel = 0;

    /**
     * init the animal and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Animal(String _nameString, Place place) {
        super(_nameString, place);
    }
    protected Animal(String _nameString,Place place,float MIN_STARTING_WEIGHT,float MAX_STARTING_WEIGHT){
        this(_nameString,place);
        setCurrentWeight(new Random().nextFloat(MIN_STARTING_WEIGHT,MAX_STARTING_WEIGHT));
    }

    /*//note de A : je teste des choses
    public Animal(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        this.mentalState = MentalState.HAPPY;
    }*/

    //modif par A : ajout difficulty
    public Animal(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety, int difficulty) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, difficulty);
        this.currentSatiety = currentSatiety; // 7
    }



    /**
     * decrease the animal health if the conditions are met
     * kill the animal if health <= 0
     * @param _satietyLost
     * @param _cleaningLost
     * @param _energyLost
     */
    public void decreaseHealth(int _satietyLost, int _cleaningLost, int _energyLost){
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


    

    /**
     * 
     * @return mean
     */
    @Override
    public float mean(){
        return (currentCleanliness+currentHealth+currentSatiety)/3;
    }


    @Override
    protected void initRoutine(){
        routine = new Thread(){
            public void run() {
                try{
                    do{
                        sleep(NB_SEC);
                        decreaseStats(mentalDifficulty, cleaningDifficulty, energyDifficulty,satietyDifficulty);
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


                        //starve and loosing weight_______________
                        starve();
                        //________________________________________


                        //increaseHealth__________________________
                        increaseHealth();
                        //________________________________________


                        //sleepCanceled___________________________
                        if(sleepCancel > 0){
                            sleepCancel--;
                        }
                        //________________________________________
                        //MentalCanceled__________________________
                        if(mentalCancel> 0){
                            mentalCancel--;
                        }
                        //________________________________________
                        //update Mental State_____________________
                        updateMentalState();
                        //________________________________________


                        //Calling observer
                        updateAllStats();

                        //
                        if(cnt%1==0){
                            save();
                        }

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
    public int getSleepCancel() {
        return sleepCancel;
    }
    public void setSleepCancel(int _sleepCancel) {
        this.sleepCancel = _sleepCancel;
    }

    @Override
    public void startSleep(){
        setSleepCancel(2);
        super.startSleep();
    }
    @Override
    public void eat(){
        if(currentSatiety+satietyGain <=100){
            currentSatiety+=satietyGain;
        }
        else{
            currentSatiety =100;
            setCurrentWeight(currentWeight+(currentWeight/10));
        }
        observer.propertyChange(new PropertyChangeEvent(this, "statsDisplay", null,null));
    }

    private void starve(){
        if(currentSatiety < 20){      
            setCurrentWeight(currentWeight-(currentWeight/10));
        }
    }

    protected void updateAllStats(){
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat1", null, getCurrentHealth()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat2", null, getCurrentEnergy()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, getCurrentCleaning()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null, getCurrentSatiety()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateMental", null, getMentalState()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeight", null, getCurrentWeight()));
    }

    protected void save(){
        observer.propertyChange(new PropertyChangeEvent(this, "save", null,null));
    }

}


