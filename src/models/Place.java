package models;

import models.Status.EPlace;
import models.Status.Weather;

public class Place {
    private int id;
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

    public Place(int id, EPlace ePlace) {
        this.currentPlace = ePlace;
        this.nextPlace = null;
        this.previousPlace = null;
        this.id = id;
        Place.weather = Weather.SUNNY;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    
    public static Weather getWeather() { return Place.weather; }
}
