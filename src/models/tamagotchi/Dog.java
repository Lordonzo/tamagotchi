package models.tamagotchi;

import java.time.LocalDateTime;

import models.Place;
import models.Status.MentalState;
import models.Status.PhysicalState;

public class Dog extends Animal {


    public Dog(String _nameString, float _weight) {
        super(_nameString, _weight);
    }

    //int, String, LocalDateTime, int, int, int, float, int, Status.PhysicalState, Status.MentalState, Place, int
    public Dog(int id, String nameString, LocalDateTime birDateTime, int currentHealth, int currentEnergy, float currentWeight, int currentCleanliness, PhysicalState state, MentalState mentalState, Place place, int slotSaved, int currentSatiety) {
        super(id, nameString, birDateTime, currentHealth, currentEnergy, currentWeight, currentCleanliness, state, mentalState, place, slotSaved, currentSatiety);
    }
}
