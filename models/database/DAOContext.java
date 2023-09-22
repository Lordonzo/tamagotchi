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

    public Connection LoadConnection() {
        dbURL = "jdbc:sqlite:sqlite"+File.separator+"db"+File.separator+"tamagotchi.db";
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(dbURL, "root", "");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
