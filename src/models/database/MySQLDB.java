package models.database;

import java.sql.*;

public abstract class MySQLDB {
    protected static String dbURL = "jdbc:mysql://localhost:3306/tamagotchi";

    /**
     * Loads the connection and checks if the database exists <p>
     * <b>Note:</b> creates also the database if it does not exist
     * @return the connection asked 
     */
    protected Connection LoadConnection() {
        try {
            Connection connection = DriverManager.getConnection(MySQLDB.dbURL, "TamagotchiDB", "Tamagotchi29$");
            return connection;
        } catch (SQLException e) {
            this.CreateDatabase();
            return LoadConnection();
        }
    }

    /**
     * Creates the database if it does not exist
     */
    private void CreateDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "TamagotchiDB", "Tamagotchi29$");
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
    public boolean CreateTable(String _tableString) {
        try (Connection connection = this.LoadConnection(); Statement statement = connection.createStatement();) {
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
    public boolean DropTable(String _tableString) {
        try (Connection connection = this.LoadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE tamagotchi."+_tableString+";");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
