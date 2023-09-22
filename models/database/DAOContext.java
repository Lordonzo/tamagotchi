package models.database;

import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOContext {
    protected static String dbURL;
    protected static String dbLogin;
    protected static String dbPassword;
    protected Connection connection;

    public void LoadConnection() {
        dbURL = "jdbc:sqlite:sqlite"+File.separator+"db"+File.separator+"tamagotchi.db";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL, "root", "");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
