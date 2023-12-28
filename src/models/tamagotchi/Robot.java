package models.tamagotchi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import models.Place;
import models.Status.EPlace;
import models.Status.MentalState;
import models.Status.Weather;

public class Robot extends Tamagotchi {
    private float damageState = 0;
    private final int MIN_MEMORY = 100;
    private int currentMemory;

    /**
     * init the robot and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Robot(String _nameString,float _weight, Place place){
        super(_nameString,_weight, place);
        this.currentMemory = 0;
        this.mentalState = MentalState.HAPPY;
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/robot.png"));
        } catch (FileNotFoundException e) {}
        
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
     * decrease the mental,cleaning,energy
     * increase memory stat
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     * @param _memory
     */
    public void decreaseStats(int _mental,int _cleaning, int _energy,int _memory){
        if(currentMemory+_memory > MIN_MEMORY) currentMemory = 100;
        else currentMemory+=_memory;
    }

    
    @Override
    protected void initRoutine(){
        routine = new Thread(){
            public void run() {
                try{
                    do{
                        sleep(NB_SEC);
                        decreaseStats(mentalDifficulty, cleaningDifficulty, energyDifficulty,satietyDifficulty); //TODO changer les valeurs 
                        decreaseHealth(satietyDifficulty, cleaningDifficulty);
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
                        //Calling observer
                        observer.propertyChange(null);
                        
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

                    } while(running.get());
                
                    
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
    public void startSleep(){
        super.startSleep();
    }



}
