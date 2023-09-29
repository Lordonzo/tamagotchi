package models;

import models.Status.EPlace;
import models.Status.Weather;

public class Place {
    private EPlace currentPlace;
    private Place previousPlace;
    private Place nextPlace;
    private static Weather weather;

    public Place() {
        
    }
}
