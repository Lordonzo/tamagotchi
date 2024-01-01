package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;
import java.util.Random;

import models.Place;
import models.Status.MentalState;

public abstract class Animal extends Tamagotchi {

    private int sleepCd = 0;

    /**
     * init the animal and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Animal(String _nameString, Place _place, int _difficulty) {
        super(_nameString, _place,_difficulty);
    }
    protected Animal(String _nameString,Place _place,int _difficulty,float MIN_STARTING_WEIGHT,float MAX_STARTING_WEIGHT){
        this(_nameString,_place,_difficulty);
        //setcurrentweight and round it to two digit
        float random = Math.round(new Random().nextFloat(MIN_STARTING_WEIGHT,MAX_STARTING_WEIGHT) *100);
        random /=100;
        setCurrentWeight(random);
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
   
    }


        /**
     * decrease the mental,cleaning and energy stats
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     */
    protected void decreaseStats(int _mental,int _cleaning, int _energy, int _satiety){
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
                    //Calling observer
                        updateAllStats();
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

    public void kitchenAction(){
        observer.propertyChange(new PropertyChangeEvent(this, "kitchenActionPrep", null,null));
        if(currentSatiety+satietyGain <=100){
            currentSatiety+=satietyGain;
        }
        else{
            currentSatiety =100;
            setCurrentWeight(currentWeight+(currentWeight/10));
        }
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null,currentSatiety));
    }

    private void starve(){
        if(currentSatiety < 20){      
            setCurrentWeight(currentWeight-(currentWeight/10));
        }
    }


    
    @Override
    protected void increaseHealth(){
        super.increaseHealth();
        if(currentSatiety > 80){
            healthInc();
        }
    }

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


