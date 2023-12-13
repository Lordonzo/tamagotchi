package models.tamagotchi;

import models.Status.Tamagotchi_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private final int MAX_SATIETY = 100;

    private int currentSatiety;
    private int satietyDifficulty;
    /**
     * @param _weight
     */
    public Animal(String _nameString, float _weight, Tamagotchi_T _animalType, int _difficulty) {
        super(_nameString,_weight,_animalType,_difficulty);
        this.currentSatiety = MAX_SATIETY;
        this.mentalState = MentalState.HAPPY;
        exit = false;
        routine = new Thread(){
            public void run() {
                try{
                do{
                    sleep(NB_SEC);
                    currentEnergy-=2;
                    currentSatiety-=10;
                    currentCleaning-=10;
                    if(mean() > 50){
                        currentMental+=10;
                        if(currentMental >100){
                            currentMental = 100;
                        }
                    }
                    else{
                        currentMental-=15;
                    }


                    if(currentEnergy <0){
                        currentEnergy =0;
                    }
                    if(currentCleaning <0){
                        currentCleaning =0;
                    }
                    


                    else if(currentMental <= 0){
                        die("Suicide");
                    }


                    if(DEBUG){
                    System.out.println("currentHealth :"+currentHealth);
                    System.out.println("currentMental:"+currentMental);
                    }

                }while(!exit);
                
    }
    catch(Exception e){
        //TODO routine d'erreur
        System.err.println("Erreur du Thread");
    }
        }
    };
    }
    /**
     * increase currentSatiety
     */
    public void eat() {
        if(currentSatiety+satietyDifficulty <=100){
            currentSatiety+=satietyDifficulty;
        }
        else{ currentSatiety =100;
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
        if(currentCleaning <= 0){
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

    public void die(String _cause){
        System.out.println("L'animal est mort de : " +_cause);
    }

    public static void main(String[] args) {
        Animal a1 = new Animal("test", 10, Tamagotchi_T.CAT,1);
        a1.start();
    }

    /**
     * 
     * @return mean
     */
    public float mean(){
        return (currentCleaning+currentHealth+currentSatiety)/3;
    }
}


