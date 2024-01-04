package models.database;

import java.sql.*;

import controller.ErrorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

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

    /**
     * error handle
     * @param e
     */
    public void error(SQLException e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Error.fxml"));
            Pane root = (Pane) loader.load();
            ErrorController errorController = loader.getController();
            errorController.errorHandle(e);
            Scene scene = new Scene(root);
            scene.setRoot(root);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        
    }
}
