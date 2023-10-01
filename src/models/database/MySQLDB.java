package models.database;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            System.out.println("Connected");
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
     */
    public boolean CreateTable(String _tableString) {
        Connection connection = this.LoadConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE tamagotchi."+_tableString+" (id INTEGER NOT NULL, PRIMARY KEY (id));");
            System.out.println("Table Created");
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
     */
    public boolean DropTable(String _tableString) {
        Connection connection = this.LoadConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE tamagotchi."+_tableString+";");
            System.out.println("Table Dropped");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
