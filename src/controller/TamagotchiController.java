package controller;

import java.util.ArrayList;
import models.Place;
import models.Status.EPlace;

public class TamagotchiController {
    private ArrayList<Place> allPlaces = new ArrayList<Place>();

    public TamagotchiController() {
        this.SetPlaces();
        DisplayPlaces();
    }

    private void SetPlaces() {
        Place bedroom = new Place(EPlace.BEDROOM);
        Place garden = new Place(EPlace.GARDEN);
        Place kitchen = new Place(EPlace.KITCHEN);
        Place livingroom = new Place(EPlace.LIVINGROOM);
        Place toilet = new Place(EPlace.TOILET);
        bedroom.setNextPlace(livingroom);
        bedroom.setPreviousPlace(toilet);

        livingroom.setNextPlace(garden);
        livingroom.setPreviousPlace(bedroom);

        garden.setNextPlace(kitchen);
        garden.setPreviousPlace(livingroom);

        kitchen.setNextPlace(toilet);
        kitchen.setPreviousPlace(garden);

        toilet.setNextPlace(bedroom);
        toilet.setPreviousPlace(kitchen);

        allPlaces.add(bedroom);
        allPlaces.add(livingroom);
        allPlaces.add(garden);
        allPlaces.add(kitchen);
        allPlaces.add(toilet);
    }

    public void DisplayPlaces() {
        for (Place place : allPlaces) {
            System.out.println(place.getCurrentPlace().name());
        }
    }
}
