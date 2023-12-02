package models.database;

import java.sql.*;
import models.Status.Animal_T;
import models.tamagotchi.Animal;
import models.tamagotchi.Tamagotchi;

public class TamagotchiDB{
    /**
     * // TODO
     * @param _name
     * @param _birthDate
     * @return
     */

    /*
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
/*
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

    // TODO
    public void getAllGames() {}
*/

    /*
     * testing TODO
     */
    public static void main(String[] args){
        Tamagotchi tam1 = new Animal("test1",32,Animal_T.CAT);
        TamagotchiDB DB = new TamagotchiDB();
        String password = "tempPass"; // TODO changer
        String url = "jdbc:sqlite:save/sample.db";
        /*ProcessBuilder builder = new ProcessBuilder("GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '"+password+"' WITH GRANT OPTION;");
        try {
            Process p = builder.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }*/
        try {
        //Class.forName("org.sqlite.JDBC");
        /*Connection con =  DriverManager.getConnection(url,"TamagotchiDB", "TamagotchiDB");
        Statement st = con.createStatement();
        int result = st.executeUpdate("CREATE DATABASE Tamagotchi");
       Statement ps = con.createStatement();*/
       Connection con = DriverManager.getConnection(url);
       Statement ps = con.createStatement();
       //ps.executeUpdate("create table wat (a);");
      // DB.createTable("db");
       //DB.insertInTable(tam1);
        //DB.toString();
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
}
