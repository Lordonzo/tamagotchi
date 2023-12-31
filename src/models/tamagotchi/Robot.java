package models.tamagotchi;

import java.beans.PropertyChangeEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.time.LocalDateTime;

import javafx.scene.image.Image;
import models.Place;
import models.Status.EPlace;
import models.Status.MentalState;
import models.Status.Weather;

public class Robot extends Tamagotchi {
    private float damageState = 0; //TODO USELESS
    private final int MIN_MEMORY = 100;
    private int currentMemory;
    //début ajouté par A
    private final int MIN_BATTERY = 100;
    private int currentBattery;
    //fin ajouté par A

    /**
     * init the robot and the adequate game routine
     * init exit to false
     * @param _nameString
     * @param _weight
     * @param place
     */
    public Robot(String _nameString,Place _place,int _difficulty){
        super(_nameString,_place,_difficulty);
        this.currentMemory = 0;
        setCurrentWeight(2000);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/robot.png"));
        } catch (FileNotFoundException e) {}
        
    }

   /* //note de A : je teste des trucs
    public Robot(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/robot.png"));
        } catch (FileNotFoundException e) {}
    }*/

    //ajouté par A
    public Robot(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentBattery, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentMemory, int difficulty) {
        super(id, nameString, birDateTime, currentHealth, currentBattery, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentMemory, difficulty);
    }

    public int getCurrentMemory() {
        return currentMemory;
    }

    //ajouté par A
    public int getCurrentBattery() {
        return currentBattery;
    }

    /**
     * decrease the Robot health if the conditions are met
     * kill the animal if health <= 0
     * @param _memoryLost
     * @param _cleaningLost
     */
    public void decreaseHealth(int _memoryLost,int _cleaningLost,int _energyLost){
        if(currentMemory >= MIN_MEMORY){
            if(currentHealth-_memoryLost < 0) currentHealth = 0;
            else currentHealth-=_memoryLost;
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

    @Override
    protected void increaseHealth(){
        super.increaseHealth();
        if(currentMemory < 20){
            healthInc();
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
    public void updateStats(int _mental,int _cleaning, int _energy,int _memory){
        if(currentMemory+_memory > MIN_MEMORY) currentMemory = 100;
        else currentMemory+=_memory;
        //Damages
        if(currentHealth !=0){
            int memory = 0;
            int clean = 0;
            int energy = 0;
            if(currentMemory > 80){
                if(currentMemory > 90) memory = 10;
                else memory = 5;
            }
            if(currentCleanliness < 20){
                if(currentCleanliness < 10) clean=10;
                else clean = 5;
            }
            if(currentEnergy < 20){
                if(currentEnergy < 10) energy= 10;
                else energy = 5;
            }
            decreaseHealth(memory,clean,energy);
        }
    }

    
    @Override
    protected void initRoutine(){
        routine = new Thread(){
            public void run() {
                try{
                    do{
                        sleep(NB_SEC);
                        updateStats(mentalDifficulty, cleaningDifficulty, energyDifficulty,satietyDifficulty);
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



                        //increaseHealth__________________________
                        increaseHealth();
                        //________________________________________


                        //________________________________________
                        //gardenActionCd__________________________
                        if(gardenActionCd> 0){
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
    

    protected void updateAllStats(){
        System.out.println(getCurrentCleaning());
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat1", null, getCurrentHealth()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat2", null, getCurrentEnergy()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, getCurrentCleaning()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null, getCurrentMemory()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateMental", null, getCurrentMental()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeight", null, getCurrentWeight()));
    }



}
