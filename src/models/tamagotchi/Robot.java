package models.tamagotchi;

import models.Status.MentalState;
import models.Status.Tamagotchi_T;

public class Robot extends Tamagotchi {
    private float damageState = 0;
    public Robot(String _nameString,float _weight,int _difficulty){
        super(_nameString,_weight,Tamagotchi_T.ROBOT,_difficulty);
        this.mentalState = MentalState.HAPPY;
    }


}
