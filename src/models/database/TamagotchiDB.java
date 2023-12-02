package models.database;

import java.sql.*;
import models.Status.Animal_T;
import models.tamagotchi.Animal;
import models.tamagotchi.Tamagotchi;

public class TamagotchiDB{
    private static String dbURL = "jdbc:sqlite:save/saves.db";


    /**
     * // TODO
     * @param _name
     * @param _birthDate
     * @return
     */

    public boolean insertInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * // TODO
     * @param _tamagotchi
     * @return
     */

    public boolean updateInTable(Tamagotchi _tamagotchi) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("INSERT INTO tamagotchi.tamagotchi VALUES ()");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

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
            statement.executeUpdate("CREATE DATABASE save");
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
            statement.executeUpdate("DROP TABLE saves."+_tableString+";");
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * Save a Tamagotchi to the database
     * @param _tamagotchi
     * @return a boolean if everything went well
     */
    public boolean save(Tamagotchi _tamagotchi,int _gameID){
        try{
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            String sql = "UPDATE Tamagotchi"+
                         " SET name = '"+_tamagotchi.getName()+ "', birthDay = '"+_tamagotchi.getBirthDate()+"',deathDay ='"+_tamagotchi.getDeathDate()+
                                     "', health = '"+_tamagotchi.getCurrentHealth()+"',energy ='"+_tamagotchi.getCurrentEnergy()+"',mental ="+_tamagotchi.getState().valueOf(_tamagotchi.getState().name()).ordinal()+""+
                         " WHERE ID = "+_gameID+";";
            statement.executeUpdate(sql);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }


    }

    // TODO
    public void getAllGames() {}


    /*
     * testing de savegarde TODO
     */
    public static void main(String[] args){
        Tamagotchi tam1 = new Animal("test1",32,Animal_T.CAT);
        TamagotchiDB DB = new TamagotchiDB();
        String url = "jdbc:sqlite:save/saves.db";
        DB.save(tam1, 1);


    }
}
