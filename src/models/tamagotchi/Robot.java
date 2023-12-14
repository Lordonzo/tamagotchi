package models.tamagotchi;

import models.Status.MentalState;
import models.Status.Tamagotchi_T;

public class Robot extends Tamagotchi {
    private float damageState = 0;
    private final int MIN_MEMORY = 100;
    private int currentMemory;
    public Robot(String _nameString,float _weight){
        super(_nameString,_weight,Tamagotchi_T.ROBOT);
        this.currentMemory = 0;
        this.mentalState = MentalState.HAPPY;
        routine = new Thread(){
            public void run() {
                try{
                    do{
                        sleep(NB_SEC);

                        decreaseStats(10, 10, 10); //TODO changer les valeurs 
                        decreaseHealth(10, 10);
                        if(DEBUG){
                        System.out.println("mean : " + mean());
                        System.out.println("currentCleaning :"+currentCleaning);
                        System.out.println("currentMemory :"+currentMemory);
                        System.out.println("currentEnergy"+currentEnergy);
                        System.out.println("currentHealth :"+currentHealth);
                        System.out.println("currentMental:"+currentMental);
                        }


                    }while(!exit);
                
                }
                catch(Exception e){
                    //TODO routine d'erreur
                    System.err.println("Thread error : "+e.getMessage());
                }
            }
        };
        routine.start();
    } 

    /**
     * decrease the Robot health if the conditions are met
     * kill the animal if health <= 0
     * @param _memoryLost
     * @param _cleaningLost
     */
    public void decreaseHealth(int _memoryLost, int _cleaningLost){
        if(currentMemory >= MIN_MEMORY){
            if(currentHealth-_memoryLost < 0) currentHealth = 0;
            else currentHealth-=_memoryLost;
        }
        if(currentCleaning <= 0){
            if(currentHealth-_cleaningLost < 0) currentHealth = 0;
            else currentHealth-=_cleaningLost;
        }
        if(currentHealth <= 0){
            die("Mal traitance.");
        }
    }

    public void takingDamage(int _damage){
        //TODO when it's ranning, the bot takes damages
    }


}
