package models.database;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySQLDB {
    protected static String dbURL = "jdbc:mysql://localhost:3306/tamagotchi";

    /**
     * load the connection
     * @return the connection asked and create the database if does not exist
     */
    public Connection LoadConnection() {
        try {
            System.out.println("Connected");
            return DriverManager.getConnection(MySQLDB.dbURL, "TamagotchiDB", "Tamagotchi29$");
        } catch (SQLException e) {
            this.CreateDatabase();
            return LoadConnection();
        }
    }

    /**
     * creates the database if it does not exist
     */
    private void CreateDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "TamagotchiDB", "Tamagotchi29$");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE tamagotchi");
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    /**
     * creates a table if it does not exist
     * @param _tableString name of table to create
     */
    public void CreateTable(String _tableString) {
        Connection connection = this.LoadConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE tamagotchi."+_tableString+";");
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    /**
     * drops a table if it exists
     * @param _tableString
     */
    public void DropTable(String _tableString) {
        Connection connection = this.LoadConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE tamagotchi."+_tableString+";");
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        }
    }
}
