package models;

public class Status {
    public enum PhysicalState {
        DEAD, IN_SHAPE, INJURED, ILL
    }

    public enum MentalState {
        HAPPY, SAD, ANGRY
    }

    public enum Weather {
        RAINY, SUNNY, CLOUDY, SNOWY, THUNDER
    }

    public enum EPlace {
        TOILET, GARDEN, KITCHEN, BEDROOM, LIVINGROOM
    }
}
