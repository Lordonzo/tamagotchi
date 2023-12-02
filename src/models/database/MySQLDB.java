package models.database;

import java.sql.*;

public abstract class MySQLDB {
    protected static String dbURL = "jdbc:sqlite:SQL/tamagotchi.sql";

    /**
     * Loads the connection and checks if the database exists <p>
     * <b>Note:</b> creates also the database if it does not exist
     * @return the connection asked 
     */
    protected Connection loadConnection() {
        try {
            Connection connection = DriverManager.getConnection(MySQLDB.dbURL);
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
            Connection connection = DriverManager.getConnection(MySQLDB.dbURL);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE tamagotchi");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a table if it does not exist
     * @param _tableString name of table to create
     * @return a boolean if everything went well
     */
    public boolean createTable(String _tableString) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE tamagotchi."+_tableString+" (id INTEGER NOT NULL, PRIMARY KEY (id));");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Drops a table if it exists
     * @param _tableString
     * @return a boolean if everything went well
     */
    public boolean dropTable(String _tableString) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE tamagotchi."+_tableString+";");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
