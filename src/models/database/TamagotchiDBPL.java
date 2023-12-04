package models.database;

import java.sql.*;
import models.Status.Tamagotchi_T;
import models.tamagotchi.Animal;
import models.tamagotchi.Tamagotchi;

public class TamagotchiDBPL {
    private static String dbURL = "jdbc:sqlite:resources/data/saves.db";


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
    public boolean save(Tamagotchi _tamagotchi, int _gameID){
        try{
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            String sql = "UPDATE Tamagotchi"+
                         " SET name = '"+_tamagotchi.getName()+ "', birthDay = '"+_tamagotchi.getBirthDate().getTime()+"',deathDay ='"+_tamagotchi.getDeathDate().getTime()+
                                     "', health = '"+_tamagotchi.getCurrentHealth()+"',energy ='"+_tamagotchi.getCurrentEnergy()+"',mental ='"+_tamagotchi.getMentalState().valueOf(_tamagotchi.getMentalState().name()).ordinal()+
                                     "',physical = '"+_tamagotchi.getState().valueOf(_tamagotchi.getState().name()).ordinal()+"',type = "+_tamagotchi.getType().valueOf(_tamagotchi.getType().name()).ordinal()+
                                     " WHERE ID = "+_gameID+";";
            statement.executeUpdate(sql);
            return true;
        }
        catch(Exception e){
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }


    }

    /**
     * Get All Games name
     * Used in the menu
     * @param none
     * @return a String array of all games name
     */
    public String[] getAllGames() {
        String[] gamesName = new String[3];
        try {
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            String sql = "SELECT name FROM Tamagotchi";
            ResultSet res = statement.executeQuery(sql);
            int i = 0;
            while(res.next()){
                gamesName[i] = res.getString("name");
                i++;
            }
            return gamesName;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }


    }


    /*
     * testing saving game TODO
     */
    public static void main(String[] args){
        Tamagotchi tam0 = new Animal("test1",32,Tamagotchi_T.CAT);
        Tamagotchi tam1 = new Animal("patrick",1000,Tamagotchi_T.ROBOT);
        Tamagotchi tam2 = new Animal("josué",32,Tamagotchi_T.DOG);
        TamagotchiDBPL DB = new TamagotchiDBPL();
        DB.save(tam0, 0);
        DB.save(tam1, 1);
        DB.save(tam2, 2);
        String[] gamesName = DB.getAllGames();
        for(int i= 0; i < gamesName.length ; i++){
            System.out.println("Game "+i+": "+gamesName[i]);
        }
        //DB.save(tam1, 1);
        try {        
            Connection connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
