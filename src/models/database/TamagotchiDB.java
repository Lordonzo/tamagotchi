package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import models.Place;
import models.Status.MentalState;
import models.Status.PhysicalState;
import models.tamagotchi.*;

public class TamagotchiDB extends AbstractDB {
    /**
     * 
     */
    public void createTable() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tamagotchi"
            + "("
                + "id INTEGER NOT NULL PRIMARY KEY," // 1
                + "name VARCHAR(255) NOT NULL," // 2
                + "dateBirth TIMESTAMP NOT NULL," // 3
                + "lastTimeChanged TIMESTAMP NOT NULL," // 4
                + "health INTEGER NOT NULL," // 5
                + "energy INTEGER NOT NULL," // 6
                + "satiety INTEGER NOT NULL," // 7
                + "weightT FLOAT NOT NULL," // 8
                + "cleanliness INTEGER NOT NULL," // 9
                + "physicalState VARCHAR(255) NOT NULL," // 10
                + "animalType VARCHAR(255) NOT NULL," // 11
                + "mentalState VARCHAR(255) NOT NULL," // 12
                + "currentPlace INTEGER NOT NULL REFERENCES place(id)," // 13
                + "slotSaved INTEGER NOT NULL" // 14 -- 0 = pas affiché 
            + ")");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all rows in <b>tamagotchi</b> table
     */
    public ResultSet select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM tamagotchi");
            //LocalDateTime dateBirth = result.getTimestamp(3).toLocalDateTime();
            while (result.next()) for (int i=1; i<=result.getMetaData().getColumnCount(); i++) System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i));
            //System.out.println(dateBirth.toString());
            connection.close();
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param name
     */
    public Tamagotchi select(String name) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE name=?");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            for (int i=1; i<=result.getMetaData().getColumnCount(); i++) System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i));
            System.out.println(result.getString(10));
            // TODO switches
            if (result.getString(1) == null) return null;
            switch (result.getString(11)) {
                case "Dog" :
                    Tamagotchi dog = new Dog(result.getString("name"), result.getFloat("weightT"));
                    return dog;
                case "Cat" :
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Tamagotchi> selectSlotSaved(ArrayList<Place> places) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE slotSaved IS NOT NULL");
            ResultSet result = statement.executeQuery();
            if (result.getString(1) == null) return null;
            ArrayList<Tamagotchi> tg = new ArrayList<Tamagotchi>();
            while (result.next()) {
                switch (result.getString(11)) {
                    case "Dog" : // No 4, No 11
                        Place p = null;
                        for (Place place : places) if (result.getInt(13) == place.getCurrentPlace().ordinal()+1) p = place;
                        Tamagotchi dog = new Dog(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getFloat(8), 
                            result.getInt(9), 
                            PhysicalState.valueOf(result.getString(10)), 
                            MentalState.valueOf(result.getString(12)), 
                            p, 
                            result.getInt(14),
                            result.getInt(7)
                        );
                        tg.add(dog);
                    case "Cat" :
                }
            }
            connection.close();
            return tg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 
     * @param animal
     */
    public void add(Animal animal) {
        try (Connection connection = this.loadConnection();) {
            //LocalDateTime localDateTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, cleanliness, physicalState, mentalState, animalType, currentPlace, slotSaved) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, animal.getName());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(4, animal.getCurrentHealth());
            statement.setInt(5, animal.getCurrentEnergy());
            statement.setInt(6, animal.getCurrentSatiety());
            statement.setFloat(7, animal.getCurrentWeight());
            statement.setInt(8, animal.getCurrentCleaning());
            statement.setString(9, animal.getState().name());
            statement.setString(10, animal.getMentalState().name());
            statement.setString(11, animal.getClass().getSimpleName());
            statement.setInt(12, animal.getCurrentPlace().getCurrentPlace().ordinal()+1);
            statement.setInt(13, 1);
            // TODO Slot pris + libération du slot si y'a
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT id FROM tamagotchi WHERE name=?");
            statement.setString(1, animal.getName());
            ResultSet result = statement.executeQuery();
            animal.setId(result.getInt(1));
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * 
     * @param animal
     */
    public void update(Animal animal) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("UPDATE tamagotchi SET "
            + " health=" + animal.getCurrentHealth() 
            + " energy=" + animal.getCurrentEnergy()
            + " satiety=" + animal.getCurrentSatiety() 
            + " weightT=" + animal.getCurrentWeight()
            + " cleanliness=" + animal.getCurrentCleaning()
            + " physicalState=" + animal.getState().toString()
            + " mentalState=" + animal.getMentalState().toString()
            + " currentPlace=" + animal.getCurrentPlace().getCurrentPlace()
            + " WHERE name=" + animal.getName());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        TamagotchiDB database = new TamagotchiDB();
        database.createTable();
        //Animal dog = new Dog("doberman", 20);
        //database.add(dog);
        database.select("doberman");
    }
}
