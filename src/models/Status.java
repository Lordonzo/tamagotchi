package models;

public class Status {
    public enum PhysicalState {
        DEAD, IN_SHAPE, INJURED, ILL
    }

    //TODO j'ai modif les status juste pour voir si ca marche mais azi jsp pas trop comment on gere le robot
    public enum MentalState {
        HAPPY, SAD, ANGRY,ROBOT
    }

    public enum Weather {
        RAINY, SUNNY, CLOUDY, SNOWY, THUNDER
    }

    public enum EPlace {
        TOILET, GARDEN, KITCHEN, BEDROOM, LIVINGROOM
    }

    public enum Tamagotchi_T {
        CAT, DOG, RABBIT,ROBOT
    }
}
