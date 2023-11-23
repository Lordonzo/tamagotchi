package models.database;

import java.sql.*;

import models.tamagotchi.Tamagotchi;

public class TamagotchiDB extends MySQLDB {
    /**
     * // TODO
     * @param _name
     * @param _birthDate
     * @return
     */
    public boolean insertInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * // TODO
     * @param _tamagotchi
     * @return
     */
    public boolean updateInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // TODO
    public void getAllGames() {}
}
