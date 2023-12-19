package models.tamagotchi;

import java.time.LocalDateTime;

import models.Place;

public class Rabbit extends Animal {

    public Rabbit(String _nameString, float _weight) {
        super(_nameString, _weight);
        //TODO Auto-generated constructor stub
    }
    
    //int, String, LocalDateTime, int, int, int, float, int, int, Place, int
    public Rabbit(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety);
    }
}
