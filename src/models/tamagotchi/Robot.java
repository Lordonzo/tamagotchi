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
    protected final int MIN_MEMORY = 100;
    protected final int MAX_MEMORY = 0;
    protected final int memoryGain = 10;
    protected int currentMemory;
    protected int memoryDifficulty;


    //début ajouté par A
    protected final int MIN_BATTERY = 100;
    protected int currentBattery;
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
        this.currentMemory = MAX_MEMORY;
        setCurrentWeight(2000);
        setImage("src/resources/tama_sprites/robot.png");
        setUIString("health","energy","cleanliness","memory","charge","unplug","clear","play","clean","yipee");        
        
    }

   /* //note de A : je teste des trucs
    public Robot(String _nameString, int difficulty, Place place) {
        super(_nameString, difficulty, place);
        try {
            image = new Image(new FileInputStream("resources/tama_sprites/robot.png"));
        } catch (FileNotFoundException e) {}
    }*/

    //ajouté par A
    public Robot(int id, String nameString, LocalDateTime birDateTime,LocalDateTime lastTimeChanged, int currentHealth, int currentEnergy, int currentCleanliness, int currentMemory, float currentWeight, int mentalState, Place place, int slotSaved, int difficulty) {
        super(id, nameString, birDateTime,lastTimeChanged, currentHealth, currentEnergy, currentCleanliness, currentWeight, mentalState, place, slotSaved, difficulty);
        this.currentMemory = currentMemory;
        setUIString("health","energy","cleanliness","memory","charge","unplug","clear","play","clean","yipee");
        setImage("src/resources/tama_sprites/robot.png");
    }

@Override
public void setDifficulty(int _difficulty) {
    // TODO Auto-generated method stub
    super.setDifficulty(_difficulty);
    if(_difficulty == 1){
        this.memoryDifficulty = 2;
    }
    else if(_difficulty == 2){
        this.memoryDifficulty = 6;
    }
    else if(_difficulty == 56){
        this.memoryDifficulty = 0;
    }
    else if(_difficulty == 66){
        memoryDifficulty = 20;
    }
    else{
        memoryDifficulty = 9;
    }
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
     * decrease the mental,cleaning,energy
     * increase memory stat
     * call die routine if mental = 0
     * @param _mental
     * @param _cleaning
     * @param _energy
     * @param _memory
     */
    @Override
    public void updateStats(int _mental,int _cleaning, int _energy,int _memory){
        if(currentMemory+_memory >= MIN_MEMORY) currentMemory = 100;
        else currentMemory+=_memory;
        if(currentCleanliness-_cleaning <= 0) currentCleanliness = 0;
        else currentCleanliness-=_cleaning;
        if(currentEnergy-_energy <= 0) currentEnergy = 0;
        else currentEnergy-=_energy;
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
                    //last Connexion stats update
                    updateStats((timePassed*mentalDifficulty)/10, (timePassed*cleaningDifficulty)/10, (timePassed*energyDifficulty)/10, (timePassed*memoryDifficulty)/10);
                    //call observer
                    updateAllStats();
                    do{
                        //timeout___________________
                        int i = 0;
                        while(i < NB_SEC){
                            i+=100;
                            sleep(100);
                            //stop the timeout
                            if(!running.get() || closeGame.get()) break;
                        }
                        //stop the routine
                        if(!running.get() || closeGame.get()) break;
                        //__________________________

                        //statUpdate
                        updateStats(mentalDifficulty, cleaningDifficulty, energyDifficulty,memoryDifficulty);
                        
                        //DEBUG_________________________________________________________________
                        if(DEBUG){
                            System.out.println("mean : " + mean());
                            System.out.println("currentCleaning :"+currentCleanliness);
                            System.out.println("currentMemory :"+currentMemory);
                            System.out.println("currentEnergy"+currentEnergy);
                            System.out.println("currentHealth :"+currentHealth);
                            System.out.println("currentMental:"+currentMental);
                            System.out.println("Weather :" + currentPlace.getWeather().toString());
                            System.out.println("Counter : "+cnt);
                        }
                        //______________________________________________________________________
                        
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
    
    //ACTIONS__________________________________________________________________________
    @Override
    public void kitchenAction() {
        observer.propertyChange(new PropertyChangeEvent(this, "kitchenActionPrep", null,null));
        if(currentMemory-memoryGain > MAX_MEMORY){
            currentMemory-=memoryGain;
        }
        else{
            currentMemory =MAX_MEMORY;
        }
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null,getCurrentMemory()));
    }


    //_________________________________________________________________________________
    /**
     * call the observer to visually update the stats
     */
    protected void updateAllStats(){
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat1", null, getCurrentHealth()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat2", null, getCurrentEnergy()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat3", null, getCurrentCleaning()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateStat4", null, getCurrentMemory()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateMental", null, getMentalState()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeight", null, getCurrentWeight()));
        observer.propertyChange(new PropertyChangeEvent(this, "updateWeather", null, getCurrentWeight()));

    }



}
