package models.tamagotchi;

import models.Status.MentalState;

public abstract class Animal extends Tamagotchi {
    private final int MAX_STAT = 100;

    private int currentSatiety;
    private int satietyDifficulty;
    /**
     * @param _weight
     */
    public Animal(String _nameString, float _weight) {
        super(_nameString,_weight);
        this.currentSatiety = MAX_STAT;
        this.currentMental = MAX_STAT;
        this.currentCleanliness = MAX_STAT;
        this.mentalState = MentalState.HAPPY;
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

    
    public void thread() {
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
        routine.start();
    }
}


