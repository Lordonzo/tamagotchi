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
                + "animalType VARCHAR(255) NOT NULL," // 10
                + "mentalState INTEGER NOT NULL," // 11
                + "currentPlace INTEGER NOT NULL REFERENCES place(id)," // 12
                + "slotSaved INTEGER NOT NULL," // 13 -- 0 = pas affiché 
                + "difficulty INTEGER NOT NULL" // 14
            + ")");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all rows in <b>tamagotchi</b> table
     */
    public void select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM tamagotchi");
            System.out.println("Tamagotchi SELECT: ");
            //LocalDateTime dateBirth = result.getTimestamp(3).toLocalDateTime();
            while (result.next()) for (int i=1; i<=result.getMetaData().getColumnCount(); i++) System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i));
            //System.out.println(dateBirth.toString());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @param name
     */
    public Tamagotchi select(String name, ArrayList<Place> allPlaces) {
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
                    Tamagotchi dog = new Dog(result.getString("name"), result.getFloat("weightT"), allPlaces.get(result.getInt("currentPlace")+1));
                    return dog;
                case "Cat" : //début ajouté par A
                    Tamagotchi cat = new Cat(result.getString("name"), result.getFloat("weightT"), allPlaces.get(result.getInt("currentPlace")+1));
                    return cat;
                case "Rabbit":
                    Tamagotchi rabbit = new Rabbit(result.getString("name"), result.getFloat("weightT"), allPlaces.get(result.getInt("currentPlace")+1));
                    return rabbit;
                case "Robot":
                    Tamagotchi robot = new Robot(result.getString("name"), result.getFloat("weightT"), allPlaces.get(result.getInt("currentPlace")+1));
                    return robot;
            } //fin ajouté par A
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 
     * @param places
     * @return
     */
    public ArrayList<Tamagotchi> selectSlotSaved(ArrayList<Place> places) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE slotSaved > 0");
            ResultSet result = statement.executeQuery();
            if (result.getString(1) == null) return new ArrayList<Tamagotchi>();
            ArrayList<Tamagotchi> tg = new ArrayList<Tamagotchi>();
            while (result.next()) {
                Place p = null;
                switch (result.getString(10)) {
                    case "Dog" : // No 4, No 10
                        for (Place place : places) if (result.getInt(13) == place.getId()) p = place;
                        Tamagotchi dog = new Dog(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getFloat(8), 
                            result.getInt(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(7),
                            result.getInt(14)
                        );
                        tg.add(dog);
                    case "Cat" :
                        for (Place place : places) if (result.getInt(13) == place.getId()) p = place;
                        Tamagotchi cat = new Cat(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getFloat(8), 
                            result.getInt(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(7),
                            result.getInt(14)
                        );
                        tg.add(cat);
                    case "Rabbit" :
                        for (Place place : places) if (result.getInt(13) == place.getId()) p = place;
                        Tamagotchi rabbit = new Rabbit(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getFloat(8), 
                            result.getInt(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(7),
                            result.getInt(14)
                        );
                        tg.add(rabbit); //début ajouté par A
                    case "Robot" :
                        for (Place place : places) if (result.getInt(13) == place.getId()) p = place;
                        Tamagotchi robot = new Robot(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getFloat(8), 
                            result.getInt(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(7),
                            result.getInt(14)
                        );
                        tg.add(robot); //fin ajouté par A
                    default:
                        break;
                }
            }
            connection.close();
            return tg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<Tamagotchi>();
    }

    /**
     * 
     * @param animal
     */
    public void add(Animal animal, int slot) {
        try (Connection connection = this.loadConnection();) {
            freeSlot(slot);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, cleanliness, mentalState, animalType, currentPlace, slotSaved, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, animal.getName());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(4, animal.getCurrentHealth());
            statement.setInt(5, animal.getCurrentEnergy());
            statement.setInt(6, animal.getCurrentSatiety());
            statement.setFloat(7, animal.getCurrentWeight());
            statement.setInt(8, animal.getCurrentCleaning());
            statement.setInt(9, animal.getCurrentMental());
            statement.setString(10, animal.getClass().getSimpleName());
            statement.setInt(11, animal.getCurrentPlace().getId());
            statement.setInt(12, slot);
            statement.setInt(13, animal.getOverallDifficulty());
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

    /** //début ajouté par A
     * 
     * @param robot
     */
    public void add(Robot robot, int slot) {
        try (Connection connection = this.loadConnection();) {
            freeSlot(slot);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, cleanliness, mentalState, animalType, currentPlace, slotSaved, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, robot.getName());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(4, robot.getCurrentHealth());
            statement.setInt(5, robot.getCurrentBattery());
            statement.setInt(6, robot.getCurrentMemory());
            statement.setFloat(7, robot.getCurrentWeight());
            statement.setInt(8, robot.getCurrentCleaning());
            statement.setInt(9, robot.getCurrentMental());
            statement.setString(10, robot.getClass().getSimpleName());
            statement.setInt(11, robot.getCurrentPlace().getId());
            statement.setInt(12, slot);
            statement.setInt(13, robot.getOverallDifficulty());
            // TODO Slot pris + libération du slot si y'a
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT id FROM tamagotchi WHERE name=?");
            statement.setString(1, robot.getName());
            ResultSet result = statement.executeQuery();
            robot.setId(result.getInt(1));
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } //fin ajouté par A

    /**
     * 
     * @param slotTaken
     */
    private void freeSlot(int slotTaken) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("UPDATE tamagotchi SET slotSaved=0 WHERE slotSaved=?");
            statement.setInt(1, slotTaken);
            statement.executeUpdate();
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
            + ", energy=" + animal.getCurrentEnergy()
            + ", satiety=" + animal.getCurrentSatiety() 
            + ", weightT=" + animal.getCurrentWeight()
            + ", cleanliness=" + animal.getCurrentCleaning()
            + ", physicalState=" + animal.getState().toString()
            + ", mentalState=" + animal.getMentalState()
            + ", currentPlace=" + animal.getCurrentPlace().getCurrentPlace()
            + ", WHERE name=" + animal.getName());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

    /* //début ajouté par A
     * 
     * @param robot
     */
    public void update(Robot robot) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("UPDATE tamagotchi SET "
            + " health=" + robot.getCurrentHealth() 
            + ", energy=" + robot.getCurrentBattery()
            + ", satiety=" + robot.getCurrentMemory() 
            + ", weightT=" + robot.getCurrentWeight()
            + ", cleanliness=" + robot.getCurrentCleaning()
            + ", physicalState=" + robot.getState().toString()
            + ", mentalState=" + robot.getMentalState()
            + ", currentPlace=" + robot.getCurrentPlace().getCurrentPlace()
            + ", WHERE name=" + robot.getName());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    } //fin ajouté par A


    public static void main(String[] args) {
        TamagotchiDB database = new TamagotchiDB();
        database.createTable();
        //Animal dog = new Cat("cat", 20);
        //database.add(dog);
        //dog = new Dog("doberman", 20);
        //database.add(dog);
        database.select();
    }
}
