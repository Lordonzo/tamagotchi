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
        allPlaces.clear(); // TODO j'ai rajouté ac pour etre sur
        Place bedroom = new Place(0,EPlace.BEDROOM);
        Place livingroom = new Place(1,EPlace.LIVINGROOM);
        Place garden = new Place(2,EPlace.GARDEN);
        Place kitchen = new Place(3,EPlace.KITCHEN);
        Place toilet = new Place(4,EPlace.TOILET);
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
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<Place> getPlaces() { return this.allPlaces; }
}
