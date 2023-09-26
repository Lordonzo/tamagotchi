package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import models.Player;

public class PlayerDAO extends DAOContext {
    
    @Override
    public boolean CreateTable() {
        Connection connection = this.LoadConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE tamagotchi.player;");
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
