package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;

import models.Place;
import models.Status.MentalState;

public abstract class Animal extends Tamagotchi {


    /**
     * init the animal and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Animal(String _nameString, float _weight, Place place) {
        super(_nameString,_weight, place);
        this.mentalState = MentalState.HAPPY;

        //TODO changer la difficulté
        setDifficulty(3);
    }

    public Animal(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved);
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
        if(currentHealth <= 0){
            die("Mal traitance.");
        }
    }


    

    /**
     * rewrite
     * @return mean
     */
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
                        //decreaseHealth(satietyDifficulty, cleaningDifficulty);
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

                        //Electrocution_______________
                        ranningEvent(rainDamage,mentalDifficulty);


                        //starve and loosing weight________________
                        starve();

                        //cleaning________________________________

                        
                        //Calling observer
                        observer.propertyChange(new PropertyChangeEvent(this,"statsDisplay",null,null));

                        

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

    public void starve(){
        if(currentSatiety < 20){      
            if(currentSatiety < 10){
                decreaseHealth(10,0,0);
                setCurrentWeight(currentWeight-(currentWeight/10));
            }
            else{
                decreaseHealth(5,0,0);
                setCurrentWeight(currentWeight-(currentWeight/10));
            }
        }
    }

}


