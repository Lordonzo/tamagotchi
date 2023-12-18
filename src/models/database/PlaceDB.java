package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Place;
import models.Status.EPlace;

public class PlaceDB extends AbstractDB {
    /**
     * 
     * @param allPlaces
     */
    public void createTable(ArrayList<Place> allPlaces) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS place"
            + "("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "nextPlace INTEGER NOT NULL REFERENCES tamagotchi(id),"
                + "previousPlace INTEGER NOT NULL REFERENCES tamagotchi(id)"
            + ")");
            for (Place place : allPlaces) this.add(place);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<Place> select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM place");
            ArrayList<Place> tp = this.setAsObject();
            while (result.next()) for (int i=0; i<tp.size(); i++) tp.get(i).setId(result.getInt(1));
            connection.close();
            return tp;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @return
     */
    private ArrayList<Place> setAsObject() {
        ArrayList<Place> arrayList = new ArrayList<Place>();
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

        arrayList.add(bedroom);
        arrayList.add(livingroom);
        arrayList.add(garden);
        arrayList.add(kitchen);
        arrayList.add(toilet);
        return arrayList;
    }

    /**
     * 
     * @param place
     */
    public void add(Place place) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO place (name, previousPlace, nextPlace) VALUES (?, ?, ?)");
            statement.setString(1, place.getCurrentPlace().name());
            statement.setInt(2, place.getPreviousPlace().getCurrentPlace().ordinal()+1);
            statement.setInt(3, place.getNextPlace().getCurrentPlace().ordinal()+1);
            statement.executeUpdate();
            statement = connection.prepareStatement("SELECT id FROM place WHERE name=?");
            statement.setString(1, place.getCurrentPlace().name());
            ResultSet result = statement.executeQuery();
            place.setId(result.getInt(1));
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @param place
     */
    public void update(Place place) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("UPDATE place SET nextPlace=? previousPlace=? WHERE name=?");
            statement.setString(3, place.getCurrentPlace().name());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
