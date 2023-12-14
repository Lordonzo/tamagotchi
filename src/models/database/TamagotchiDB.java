package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import models.tamagotchi.*;

public class TamagotchiDB extends MySQLDB {
    
    /**
     * 
     */
    @Override
    public boolean createTable() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tamagotchi"
            + "("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "dateBirth TIMESTAMP NOT NULL,"
                + "lastTimeChanged TIMESTAMP NOT NULL,"
                + "health INT NOT NULL,"
                + "energy INT NOT NULL,"
                + "satiety INT NOT NULL,"
                + "weightT FLOAT NOT NULL,"
                + "toilet INT NOT NULL,"
                + "physicalState VARCHAR(255) NOT NULL,"
                + "animalType VARCHAR(255) NOT NULL,"
                + "mentalState VARCHAR(255) NOT NULL,"
                + "currentPlace VARCHAR(255) NOT NULL"
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
     */
    public void select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM tamagotchi");
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
    public void select(String name) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE name=?");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            //for (int i=1; i<=result.getMetaData().getColumnCount(); i++) System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i));
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @param animal
     */
    public void add(Animal animal) {
        try (Connection connection = this.loadConnection();) {
            //LocalDateTime localDateTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, toilet, physicalState, mentalState, animalType, currentPlace) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            statement.setString(12, animal.getCurrentPlace().getCurrentPlace().name());
            statement.executeUpdate();

            /**
            statement.executeQuery("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, toilet, physicalState, mentalState, animalType, currentPlace) VALUES ("
            + animal.getName()
            + ", NOW()"
            + ", NOW()"
            + ", " + animal.getCurrentHealth() 
            + ", " + animal.getCurrentEnergy()
            + ", " + animal.getCurrentSatiety() 
            + ", " + animal.getCurrentWeight()
            + ", " + animal.getCurrentCleaning()
            + ", " + animal.getState().name()
            + ", " + animal.getMentalState().name()
            + ", " + animal.getType().name()
            + ", " + animal.getCurrentPlace().getCurrentPlace().name()
            + ")");
            */
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
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        TamagotchiDB database = new TamagotchiDB();
        database.createTable();
        Animal dog = new Dog("doberman", 20);
        //database.add(dog);
        database.select("pipi");
    }
}
