package models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import controller.InGameController;
import controller.PlaceController;
import models.Place;
import models.Status.EPlace;
import models.tamagotchi.*;

public class TamagotchiDB extends AbstractDB {
    /**
     * 
     */
    public void createTable() {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tamagotchi"
            + "("
                + "id INTEGER NOT NULL PRIMARY KEY," // 1
                + "name VARCHAR(255) NOT NULL," // 2
                + "dateBirth TIMESTAMP NOT NULL," // 3
                + "lastTimeChanged TIMESTAMP NOT NULL," // 4
                + "stat1 INTEGER NOT NULL," // 5 health
                + "stat2 INTEGER NOT NULL," // 6 energy
                + "stat3 INTEGER NOT NULL," // 7 cleanliness
                + "stat4 INTEGER NOT NULL," // 8 satiety or memory
                + "weightT FLOAT NOT NULL," // 9
                + "type VARCHAR(255) NOT NULL," // 10
                + "mentalState INTEGER NOT NULL," // 11
                + "currentPlace INTEGER NOT NULL REFERENCES place(id)," // 12
                + "slotSaved INTEGER NOT NULL," // 13 -- 0 = pas affiché 
                + "difficulty INTEGER NOT NULL" // 14
            + ")");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 
     * @param places
     * @return
     */
    public ArrayList<Tamagotchi> selectSlotSaved() {
        PlaceController placeController = new PlaceController(false);
        ArrayList<Place> places = placeController.getPlaces();
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE slotSaved > 0");
            ResultSet result = statement.executeQuery();
            if (result.getString(1) == null) return new ArrayList<Tamagotchi>();
            ArrayList<Tamagotchi> tg = new ArrayList<Tamagotchi>();
            
            while (result.next()) {
                Place p = null;
                for (Place place : places) if (result.getInt("currentPlace") == place.getId()) p = place;
                switch (result.getString(10)) {
                    case "Dog" :
                        Tamagotchi dog = new Dog(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getTimestamp(4).toLocalDateTime(),
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getInt(7),
                            result.getInt(8),
                            result.getFloat(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(14)
                        );
                        tg.add(dog);
                        break;
                    case "Cat" :
                        Tamagotchi cat = new Cat(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getTimestamp(4).toLocalDateTime(),
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getInt(7),
                            result.getInt(8),
                            result.getFloat(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(14)
                        );
                        tg.add(cat);
                        break;
                    case "Rabbit" :
                        Tamagotchi rabbit = new Rabbit(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getTimestamp(4).toLocalDateTime(),
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getInt(7),
                            result.getInt(8),
                            result.getFloat(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(14)
                        );
                        tg.add(rabbit); //début ajouté par A
                        break;
                    case "Robot" :
                        Tamagotchi robot = new Robot(
                            result.getInt(1), 
                            result.getString(2), 
                            result.getTimestamp(3).toLocalDateTime(), 
                            result.getTimestamp(4).toLocalDateTime(),
                            result.getInt(5), 
                            result.getInt(6), 
                            result.getInt(7),
                            result.getInt(8),
                            result.getFloat(9), 
                            result.getInt(11), 
                            p, 
                            result.getInt(13),
                            result.getInt(14)
                        );
                        tg.add(robot); //fin ajouté par A
                        break;
                    default:
                        break;
                }
            }
            connection.close();
            return tg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<Tamagotchi>();
    }

    /**
     *
     * @param _slot
     * @return
     */
    public Tamagotchi selectBySlot(int _slot){
        Tamagotchi tamagotchi = null;
        try (Connection connection = this.loadConnection();) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tamagotchi WHERE slotSaved="+_slot+";");
            ResultSet result = statement.executeQuery();
            if(result.next()){
                Place p = new Place(EPlace.LIVINGROOM); //TODO changer
                PlaceController pc = new PlaceController(false);
                ArrayList<Place> places = pc.getPlaces();
                for (Place place : places) if (result.getInt("currentPlace") == place.getId()) p = place;
                tamagotchi = createTamagotchi(_slot, result.getString("name"), result.getTimestamp("dateBirth").toLocalDateTime(), result.getTimestamp("lastTimeChanged").toLocalDateTime(),result.getInt("stat1"),result.getInt("stat2"),result.getInt("stat3"),result.getInt("stat4"),result.getFloat("weightT"),result.getString("type"),result.getInt("mentalState"),p, result.getInt("slotSaved"),result.getInt("difficulty"));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tamagotchi;
    }

    /**
     * create a tamagotchi with all the information
     * @param _id
     * @param _name
     * @param _dateBirth
     * @param _lastTimeChanged
     * @param _health
     * @param _energy
     * @param _satiety
     * @param _weightT
     * @param _cleanliness
     * @param _type
     * @param _mental
     * @param _place
     * @param _slotTake
     * @param _difficulty
     * @return
     */
    public Tamagotchi createTamagotchi(int _id, String _name, LocalDateTime _dateBirth, LocalDateTime _lastTimeChanged,int _stat1,int _stat2,int stat3,int stat4, float _weightT,String _type,int _mental,Place _place,int _slotTake, int _difficulty){
        Tamagotchi tamagotchi;
        System.out.println("Create tamagotchi : "+_lastTimeChanged.toString());
        switch (_type) {
            case "Cat":
                tamagotchi = new Cat(_id,_name, _dateBirth,_lastTimeChanged,_stat1, _stat2, stat3 ,stat4,_weightT,_mental,_place,_slotTake,_difficulty);
                break;
            case "Robot":
                tamagotchi = new Robot(_id,_name, _dateBirth,_lastTimeChanged,_stat1, _stat2, stat3 ,stat4,_weightT,_mental,_place,_slotTake,_difficulty);
                break;
            case "Rabbit":
                tamagotchi = new Rabbit(_id,_name, _dateBirth,_lastTimeChanged,_stat1, _stat2, stat3 ,stat4,_weightT,_mental,_place,_slotTake,_difficulty);
                break;
            case "Dog":
                tamagotchi = new Dog(_id,_name, _dateBirth,_lastTimeChanged,_stat1, _stat2, stat3 ,stat4,_weightT,_mental,_place,_slotTake,_difficulty);
                break;
            default:
                //TODO Error
                tamagotchi = null;
                break;
        }

        return tamagotchi;
    }

    public void save(Tamagotchi _tamagotchi){
        if(_tamagotchi.getClass().getSimpleName().equals("Robot")){
            add(((models.tamagotchi.Robot)_tamagotchi), _tamagotchi.getId());

        }
        else{
            add(((Animal)_tamagotchi), _tamagotchi.getId());
        }
    }

    /**
     * 
     * @param animal
     */
    public void add(Animal animal, int slot) {
        try (Connection connection = this.loadConnection();) {
            PreparedStatement isSlotExisting = connection.prepareStatement("SELECT 1 FROM tamagotchi WHERE slotSaved = "+slot+";");
            ResultSet resultSlot = isSlotExisting.executeQuery();
            //if the slot is taken
            if(resultSlot.next()){
                connection.close();
                update(animal, slot);
            }
            else{
                String saveQuery = "INSERT INTO tamagotchi (id ,name, dateBirth, lastTimeChanged, stat1, stat2,stat3, stat4, weightT , mentalState, type, currentPlace, slotSaved, difficulty) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"+";";
                PreparedStatement statement = connection.prepareStatement(saveQuery);
                statement.setString(2, animal.getName());
                statement.setTimestamp(3, Timestamp.valueOf(animal.getDateBirth()));
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                statement.setInt(5, animal.getCurrentHealth());
                statement.setInt(6, animal.getCurrentEnergy());
                statement.setInt(7, animal.getCurrentCleaning());
                statement.setInt(8, animal.getCurrentSatiety());
                statement.setFloat(9, animal.getCurrentWeight());
                statement.setInt(10, animal.getCurrentMental());
                statement.setString(11, animal.getClass().getSimpleName());
                statement.setInt(12, animal.getCurrentPlace().getId());
                statement.setInt(13, slot);
                statement.setInt(14, animal.getDifficulty());
                statement.executeUpdate();
            }
            
        connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** //début ajouté par A
     * 
     * @param robot
     */
    public void add(Robot robot, int slot) {
       try (Connection connection = this.loadConnection();) {
            PreparedStatement isSlotExisting = connection.prepareStatement("SELECT 1 FROM tamagotchi WHERE slotSaved = "+slot+";");
            ResultSet resultSlot = isSlotExisting.executeQuery();
            
            //if the slot is taken
            if(resultSlot.next()){
                connection.close();
                update(robot, slot);
            }
            else{
                String saveQuery = "INSERT INTO tamagotchi (id ,name, dateBirth, lastTimeChanged, stat1, stat2,stat3, stat4, weightT , mentalState, type, currentPlace, slotSaved, difficulty) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"+";";
                PreparedStatement statement = connection.prepareStatement(saveQuery);
                statement.setString(2, robot.getName());
                statement.setTimestamp(3, Timestamp.valueOf(robot.getDateBirth()));
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                statement.setInt(5, robot.getCurrentHealth());
                statement.setInt(6, robot.getCurrentEnergy());
                statement.setInt(7, robot.getCurrentMemory());
                statement.setFloat(8, robot.getCurrentWeight());
                statement.setInt(9, robot.getCurrentCleaning());
                statement.setInt(10, robot.getCurrentMental());
                statement.setString(11, robot.getClass().getSimpleName());
                statement.setInt(12, robot.getCurrentPlace().getId());
                statement.setInt(13, slot);
                statement.setInt(14, robot.getDifficulty());
                statement.executeUpdate();
            }
            
        connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } //fin ajouté par A

    public void delete(int _slot){
         try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DELETE FROM `tamagotchi` WHERE slotSaved="+_slot+";");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Update the data
     * @param animal 
     */
    public void update(Animal animal,int slot) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("UPDATE tamagotchi SET "
            + "name=\"" + animal.getName()+"\""
            + ", stat1=" + animal.getCurrentHealth()
            + ", dateBirth=\""+ Timestamp.valueOf(animal.getDateBirth())+"\"" 
            + ", lastTimeChanged=\""+Timestamp.valueOf(LocalDateTime.now())+"\""
            + ", stat2=" + animal.getCurrentEnergy()
            + ", stat3=" + animal.getCurrentCleaning()
            + ", stat4=" + animal.getCurrentSatiety() 
            + ", weightT=" + animal.getCurrentWeight()
            + ", type=\"" + animal.getClass().getSimpleName()+"\""
            + ", mentalState=" + animal.getCurrentMental()
            + ", currentPlace=" + animal.getCurrentPlace().getId()
            +", difficulty="+animal.getDifficulty()
            + " WHERE slotSaved=" + slot+";");
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

    /* //début ajouté par A
     * 
     * @param robot
     */
    public void update(Robot robot,int slot) {
        try (Connection connection = this.loadConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("UPDATE tamagotchi SET "
            + "name=\"" + robot.getName()+"\""
            + ", stat1=" + robot.getCurrentHealth()
            + ", dateBirth=\""+ Timestamp.valueOf(robot.getDateBirth())+"\"" 
            + ", lastTimeChanged=\""+Timestamp.valueOf(LocalDateTime.now())+"\""
            + ", stat2=" + robot.getCurrentEnergy()
            + ", stat3=" + robot.getCurrentCleaning()
            + ", stat4=" + robot.getCurrentMemory() 
            + ", weightT=" + robot.getCurrentWeight()
            + ", type=\"" + robot.getClass().getSimpleName()+"\""
            + ", mentalState=" + robot.getCurrentMental()
            + ", currentPlace=" + robot.getCurrentPlace().getId()
            +", difficulty="+robot.getDifficulty()
            + " WHERE slotSaved=" + slot+";");
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    } //fin ajouté par A


    /*public static void main(String[] args) {
        PlaceController pc = new PlaceController(false);
        TamagotchiDB database = new TamagotchiDB();
        PlaceDB placeDB = new PlaceDB();
        ArrayList<Place> places = pc.getPlaces();
        database.createTable();
        Animal dog = new Rabbit("cat",places.get(1),1);
        dog.setCurrentHealth(0);
        database.add(dog,1);
        dog = new Dog("doberman",places.get(2),2);
        database.add(dog,2);
        //database.select();
        ArrayList<Tamagotchi> tamagotchis = new ArrayList<>();
        tamagotchis = database.selectSlotSaved();
        // System.out.println(tamagotchis.size());
        // System.out.println(tamagotchis.toString());
        for(Tamagotchi tamagotchi : tamagotchis){
            System.out.println(tamagotchi.getCurrentWeight());
        }
    }*/
}
