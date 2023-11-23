package models.database;

import java.sql.*;

public class SQLConnection {
    private static SQLConnection instance;
    private String ConnectionURL = "";

    public SQLConnection() {
        try (Connection connection = DriverManager.getConnection(ConnectionURL);) {

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLConnection getInstance() {
        if (SQLConnection.instance == null) SQLConnection.instance = new SQLConnection();
        return SQLConnection.instance;
    }

    public Connection getConnection() {
        return SQLConnection.instance.getConnection();
    }
}
