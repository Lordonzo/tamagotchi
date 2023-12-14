package models.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import models.Status.Tamagotchi_T;
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
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "dateBirth DATETIME NOT NULL,"
                + "lastTimeChanged DATETIME,"
                + "health INT NOT NULL,"
                + "energy INT NOT NULL,"
                + "satiety INT NOT NULL,"
                + "weightT FLOAT NOT NULL,"
                + "toilet INT NOT NULL,"
                + "physicalState VARCHAR(255) NOT NULL,"
                + "animalType VARCHAR(255) NOT NULL,"
                + "mentalState VARCHAR(255) NOT NULL,"
                + "currentPlace INTEGER NOT NULL"
            + ")");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM tamagotchi");
            while (result.next()) System.out.println(result.getString(1) + " name");
            System.out.println(result.toString());
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void select(String name) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM tamagotchi WHERE name="+name);
            System.out.println(result.toString());
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
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setDate(3, Date.valueOf(LocalDate.now()));



            statement.executeQuery("INSERT INTO tamagotchi (name, dateBirth, lastTimeChanged, health, energy, satiety, weightT, toilet, physicalState, mentalState, animalType, currentPlace) VALUES ("
            + animal.getName()
            + ", NOW()"
            + ", NOW()"
            + ", " + animal.getCurrentHealth() 
            + ", " + animal.getCurrentEnergy()
            + ", " + animal.getCurrentSatiety() 
            + ", " + animal.getCurrentWeight()
            + ", " + null
            + ", " + animal.getState().toString()
            + ", " + animal.getMentalState().toString()
            + ", " + animal.getType().toString()
            + ", " + animal.getCurrentPlace().getCurrentPlace()
            + ")");
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
        Animal dog = new Animal("doberman", 20, Tamagotchi_T.DOG);
        database.add(dog);
        database.select();
    }
}
