package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Options;

public class OptionDB extends AbstractDB {
    /**
     * 
     */
    public void createTable() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS options"
            + "("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "musicVolume DOUBLE NOT NULL DEFAULT 70,"
                + "resX INT NOT NULL DEFAULT 1080, "
                + "resY INT NOT NULL DEFAULT 720,"
                +"language VARCHAR(255) NOT NULL"
            + ")");
            connection.close();
            this.setUp();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     */
    private void setUp() {
        try (Connection connection = this.loadConnection();) {
            if (!exists()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO options (musicVolume, resX, resY, language) VALUES (?, ?, ?,?)");
                statement.setDouble(1, 0.5);
                statement.setInt(2, 1080);
                statement.setInt(3, 720);
                statement.setString(4, "fr");
                statement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if a row in database's <code>options</code> table exists
     * @return <code>true</code> if a row exists. <code>false</code> if not
     */
    private boolean exists() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM options");
            result.next();
            return result.getInt("COUNT(*)") > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 
     */
    public Options select() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM options");
            Options options = new Options(result.getDouble("musicVolume"), result.getInt("resX"), result.getInt("resY"),result.getString("language"));
            connection.close();
            return options;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param options
     */
    public void update(Options options) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("UPDATE options SET musicVolume=?, resX=?, resY=?, language=? WHERE id = 1");
            statement.setDouble(1, options.getVolume());
            statement.setInt(2, options.getResX());
            statement.setInt(3, options.getResY());
            statement.setString(4,options.getLanguage());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
