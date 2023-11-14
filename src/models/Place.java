package models;

import models.Status.EPlace;
import models.Status.Weather;

public class Place {
    private EPlace currentPlace;
    private Place previousPlace;
    private Place nextPlace;
    private static Weather weather;

    public Place(EPlace ePlace) {
        this.currentPlace = ePlace;
        this.nextPlace = null;
        this.previousPlace = null;
        Place.weather = Weather.SUNNY;
    }


    public EPlace getCurrentPlace() {
        return this.currentPlace;
    }

    public void setCurrentPlace(EPlace currentPlace) {
        this.currentPlace = currentPlace;
    }

    public Place getPreviousPlace() {
        return this.previousPlace;
    }

    public void setPreviousPlace(Place previousPlace) {
        this.previousPlace = previousPlace;
    }

    public Place getNextPlace() {
        return this.nextPlace;
    }

    public void setNextPlace(Place nextPlace) {
        this.nextPlace = nextPlace;
    }
    
    public static Weather GetWeather() { return Place.weather; }
}
