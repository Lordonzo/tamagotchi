package models.tamagotchi;

import models.Status.MentalState;
import models.Status.Tamagotchi_T;

public class Robot extends Tamagotchi {
    private float damageState = 0;
    public Robot(String _nameString,float _weight){
        super(_nameString,_weight,Tamagotchi_T.ROBOT);
        this.mentalState = MentalState.HAPPY;
    }


}
