package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Player;

public class PlayerDAO extends MySQLDB {
    public void insertInTable(Player _player) {
        Connection connection = this.LoadConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tamagotchi.player VALUES (?);");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player selectInTable() {
        Connection connection = this.LoadConnection();
        return new Player(null);
    }
}
