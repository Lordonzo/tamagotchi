package models.database;

import java.sql.*;

public abstract class AbstractDB {
    protected static String dbURL = "jdbc:sqlite:src/resources/data/tamagotchi.db";


    /**
     * Loads the connection and checks if the database exists <p>
     * <b>Note:</b> creates also the database if it does not exist
     * @return the connection asked 
     */
    protected Connection loadConnection() {
        try {
            //dropDatabase();
            Connection connection = DriverManager.getConnection(dbURL);
            return connection;
        } catch (SQLException e) {
            this.createDatabase();
            return loadConnection();
        }
    }

    /**
     * Creates the database if it does not exist
     */
    private void createDatabase() {
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            statement.executeQuery("CREATE DATABASE tamagotchi");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
