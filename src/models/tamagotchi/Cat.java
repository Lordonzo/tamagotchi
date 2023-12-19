package models.tamagotchi;

import java.time.LocalDateTime;

import models.Place;
import models.Status.MentalState;
import models.Status.PhysicalState;

public class Cat extends Animal {

    public Cat(String _nameString, float _weight) {
        super(_nameString, _weight);
        //TODO Auto-generated constructor stub
    }

    public Cat(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, int mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, mentalState, place, slotSaved, currentSatiety);
    }
    
}
