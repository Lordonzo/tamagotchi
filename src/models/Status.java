package models;

public class Status {
    public enum PhysicalState {
        DEAD, IN_SHAPE, INJURED, ILL
    }

    public enum MentalState {
        HAPPY, SAD,JOLLY,DEPRESSED
    }

    public enum Weather {
        RAINY, SUNNY, CLOUDY, SNOWY, THUNDER
    }

    public enum EPlace {
        BEDROOM, LIVINGROOM, GARDEN, KITCHEN, TOILET
    }
}
