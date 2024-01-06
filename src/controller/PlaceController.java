package controller;

import java.util.ArrayList;
import models.Place;
import models.Status.EPlace;

public class PlaceController {
    private ArrayList<Place> allPlaces = new ArrayList<Place>();

    /**
     * 
     */
    public PlaceController() {
        this.setUpPlaces();
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
        allPlaces.clear();
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
     * @return
     */
    public ArrayList<Place> getPlaces() { return this.allPlaces; }
}
