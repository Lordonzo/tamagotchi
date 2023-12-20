package controller;

import java.util.ArrayList;
import models.Place;
import models.Status.EPlace;
import models.database.PlaceDB;

public class PlaceController {
    private ArrayList<Place> allPlaces = new ArrayList<Place>();
    private PlaceDB placeDB = new PlaceDB();

    /**
     * 
     */
    public PlaceController(boolean databaseHere) {
        this.setUpPlaces();
        if (databaseHere) allPlaces = placeDB.select();
        displayPlaces();
    }

    /**
     * 
     * @param withIds
     */
    public void setIds(ArrayList<Place> withIds) {
        allPlaces = withIds;
    }

    /**
     * 
     */
    private void setUpPlaces() {
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

    /**
     * 
     */
    public void displayPlaces() {
        for (Place place : allPlaces) {
            System.out.println(place.getId() + ": " + place.getCurrentPlace().name() + " next: " + place.getNextPlace().getCurrentPlace().name() + " previous: " + place.getPreviousPlace().getCurrentPlace().name());
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<Place> getPlaces() { return this.allPlaces; }
}
