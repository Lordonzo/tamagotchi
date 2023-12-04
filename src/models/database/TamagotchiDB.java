package models.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import models.tamagotchi.Animal;

public class TamagotchiDB extends MySQLDB {
    
    /**
     * 
     */
    @Override
    public boolean createTable() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE tamagotchi"
            + "("
                + "idT INTEGER NOT NULL PRIMARY KEY,"
                + "nameT VARCHAR(255) NOT NULL,"
                + "dateBirth DATETIME NOT NULL,"
                + "dateDeath DATETIME,"
                + "health FLOAT NOT NULL,"
                + "energy FLOAT NOT NULL,"
                + "satiety FLOAT,"
                + "weightT FLOAT,"
                + "memoryR FLOAT,"
                + "toilet FLOAT,"
                + "physicalState INTEGER NOT NULL,"
                + "animalType INTEGER NOT NULL,"
                + "mentalState INTEGER NOT NULL,"
                + "currentPlace INTEGER NOT NULL"
            + ")");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 
     * @param animal
     */
    public void add(Animal animal) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            // TODO : faire deathTime
            LocalDateTime localDateTime = LocalDateTime.now();
            statement.executeUpdate("UPDATE tamagotchi SET "
            + " nameT=" + animal.getName()
            + " dateBirth=NOW()"
            + " health=" + animal.getCurrentHealth() 
            + " energy=" + animal.getCurrentEnergy()
            + " satiety=" + animal.getCurrentSatiety() 
            + " weightT=" + animal.getCurrentWeight()
            + " toilet=" + null
            + " physicalState=" + animal.getState()
            + " mentalState=" + animal.getMentalState()
            + " animalType=" + animal.getType()
            + " currentPlace=" + animal.getCurrentPlace().getCurrentPlace());
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
            + " toilet=" + null
            + " physicalState=" + animal.getState().toString()
            + " mentalState=" + animal.getMentalState().toString()
            + " currentPlace=" + animal.getCurrentPlace().getCurrentPlace());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
