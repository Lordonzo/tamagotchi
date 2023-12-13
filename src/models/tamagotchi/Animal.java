package models.tamagotchi;

import models.Status.Tamagotchi_T;
import models.Status.MentalState;

public class Animal extends Tamagotchi {
    private final int MAX_SATIETY = 100;

    private int currentSatiety;

    /**
     * @param _weight
     */
    public Animal(String _nameString, float _weight, Tamagotchi_T _animalType) {
        super(_nameString,_weight,_animalType);
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
                    if(currentSatiety <= 0){
                        currentHealth-=20;
                    }

                    if(currentHealth <= 0){
                        die("Mal traitance.");
                    }
                    else if(currentMental <= 0){
                        die("Suicide");
                    }
                    /*DEBUG
                    System.out.println("currentHealth :"+currentHealth);
                    System.out.println("currentMental:"+currentMental);
                    */
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
     * 
     */
    public void eat() {
        if(currentSatiety+10 <=100){
            setCurrentSatiety(getCurrentSatiety()+10);
        }
    }
    

    public int getMAX_SATIETY() {
        return this.MAX_SATIETY;
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
}


