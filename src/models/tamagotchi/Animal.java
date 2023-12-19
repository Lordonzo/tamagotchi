package models.tamagotchi;

import java.time.LocalDateTime;

import models.Place;
import models.Status.MentalState;
import models.Status.PhysicalState;

public abstract class Animal extends Tamagotchi {
    private int currentSatiety; // MEMORY ROBOT
    private int satietyDifficulty;

    public Animal(String _nameString, float _weight, Place place) {
        super(_nameString,_weight, place);
        this.mentalState = MentalState.HAPPY;
        this.currentSatiety = MAX_SATIETY;
        exit = false;
        routine = new Thread(){
            public void run() {
                try{
                    do{
                        sleep(NB_SEC);
                        decreaseStats(10, 10, 10); //TODO changer les valeurs 
                        decreaseHealth(10, 10);
                        if(DEBUG){
                        System.out.println("mean : " + mean());
                        System.out.println("currentCleaning :"+currentCleanliness);
                        System.out.println("currentSatiety :"+currentSatiety);
                        System.out.println("currentEnergy"+currentEnergy);
                        System.out.println("currentHealth :"+currentHealth);
                        System.out.println("currentMental:"+currentMental);
                        }
                        observer.propertyChange(null);

                    } while(!exit);
                
                }
                catch(Exception e){
                    //TODO routine d'erreur
                    System.err.println("Thread error : "+e.getMessage());
                }
            }
        };
    }

    public Animal(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved);
        this.currentSatiety = currentSatiety; // 7
    }

    /**
     * increase currentSatiety
     */
    public void eat() {
        if(currentSatiety+satietyDifficulty <=100){
            currentSatiety+=satietyDifficulty;
        }
        else{ currentSatiety =100;
            setCurrentWeight(currentWeight+(currentWeight/10));
        }
    }

    /**
     * decrease the animal health if the conditions are met
     * kill the animal if health <= 0
     * @param _satietyLost
     * @param _cleaningLost
     */
    public void decreaseHealth(int _satietyLost, int _cleaningLost){
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
     * rewrite
     * @return mean
     */
    public float mean(){
        return (currentCleanliness+currentHealth+currentSatiety)/3;
    }
    
    /**
     *
     * decrease the mental,cleaningn,energy and satiety stats
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     * @param _satiety
     */
    public void decreaseStats(int _mental,int _cleaning, int _energy,int _satiety){
        super.decreaseStats(_mental, _cleaning, _energy);
        if(currentSatiety-_satiety < 0) currentSatiety = 0;
        else currentSatiety-=_satiety;
    }

}


