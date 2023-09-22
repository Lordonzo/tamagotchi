package models.database;

import java.sql.Statement;

import models.tamagotchi.Tamagotchi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAOContext {
    protected static String dbURL = "jdbc:mysql://localhost:3306/tamagotchi";

    /**
     * 
     * @return the connection asked
     */
    public Connection LoadConnection() {
        try {
            return DriverManager.getConnection(DAOContext.dbURL, "TamagotchiDB", "Tamagotchi29$");
        } catch (SQLException e) {
            this.CreateDatabase();
            return LoadConnection();
        }
    }

    /**
     * 
     * @return true if the Database didn't exist, it also create it
     */
    public boolean CreateDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "TamagotchiDB", "Tamagotchi29$");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE tamagotchi");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Database already exists");
            return false;
        }
    }

    public abstract boolean CreateTable();
}
