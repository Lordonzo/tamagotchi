package models.database;

import java.sql.*;

public abstract class MySQLDB {
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

    private void dropDatabase() {
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            statement.executeQuery("DROP DATABASE IF EXISTS tamagotchi");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the database if it does not exist
     */
    private void createDatabase() {
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            statement.executeQuery("CREATE DATABASE IF NOT EXISTS tamagotchi");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a table if it does not exist
     * @param tableString name of table to create
     * @return a boolean if everything went well
     */
    private boolean createTable(String tableString) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE tamagotchi."+tableString+" (id INTEGER NOT NULL, PRIMARY KEY (id));");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Creates a table if it exists
     * @return a boolean if everything went well
     */
    public abstract boolean createTable();
}
