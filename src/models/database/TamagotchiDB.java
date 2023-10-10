package models.database;

import java.sql.*;

import models.tamagotchi.Tamagotchi;

public class TamagotchiDB extends MySQLDB {
    /**
     * 
     * @param _name
     * @param _birthDate
     * @return
     */
    public boolean InsertInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.LoadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 
     * @param _tamagotchi
     * @return
     */
    public boolean UpdateInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.LoadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
