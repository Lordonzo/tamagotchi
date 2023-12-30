package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.Place;
import models.database.PlaceDB;
import models.database.TamagotchiDB;
import models.tamagotchi.Tamagotchi;

public class NewOrLoadController extends AbstractController {
    @FXML
    private ImageView slot1Btn;
    @FXML
    private ImageView slot2Btn;
    @FXML
    private ImageView slot3Btn;

    @FXML
    private Pane isDeathPane;
    private TamagotchiDB tamagotchiDB;

    //TODO pue la merde
    private boolean Exists; // indique si un tama existe ou non

    // TODO AFFICHER QUAND TU REVIENS
    //TODO ingamecontrooler.setNameLabel  POUR QUAND ON CHARGE UNE PARTIE

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlaceDB placeDB = new PlaceDB();
        ArrayList<Place> places = placeDB.select();


        tamagotchiDB = new TamagotchiDB();
        tamagotchiDB.select();
        ArrayList<Tamagotchi> selectSlotSaved = tamagotchiDB.selectSlotSaved();
        System.out.println(selectSlotSaved);
        if (!selectSlotSaved.isEmpty()) {
            Exists = true;
            System.out.println(selectSlotSaved.get(0).getName());
            for(Tamagotchi tamagotchi : selectSlotSaved){
                String path = "";
                switch (tamagotchi.getClass().getSimpleName()) {
                    case "Dog":
                        path = "src/resources/tama_sprites/dog.png";
                        break;
                    case "Cat":
                        path = "src/resources/tama_sprites/cat.png";
                        break;
                    case "Rabbit":
                        path = "src/resources/tama_sprites/rabbit.png";
                        break;
                    case "Robot":
                        path = "src/resources/tama_sprites/robot.png";
                        
                        break;
                }
                if(tamagotchi.getId()==1){
                    try {
                        slot1Btn.setImage(new Image(new FileInputStream(path)));
                    } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                }
                else if(tamagotchi.getId() == 2){
                    try {
                    slot2Btn.setImage(new Image(new FileInputStream(path)));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                }
                else if(tamagotchi.getId() == 3){
                    try {
                    slot3Btn.setImage(new Image(new FileInputStream(path)));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                }

            }
        }
    }

    @FXML 
    private void toMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
        Pane root = (Pane) loader.load();
        MenuController menuController = loader.getController();
        menuController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML 
    private void toNewTama(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewTama.fxml"));
        Pane root = (Pane) loader.load();
        NewTamaController newTamaController = loader.getController();
        newTamaController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
 
    private void toInGame(ActionEvent actionEvent,Tamagotchi tamagotchi) throws IOException {
        if(tamagotchi.getCurrentHealth() >0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
            Pane root = (Pane) loader.load();
            InGameController inGameController = loader.getController();
            inGameController.initTamagotchi(tamagotchi);
            tamagotchi.setObserver(inGameController);
            inGameController.setMusic(music);
            Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root);
        }
        else{
            isDeathPane.setVisible(true);
        }
        }


    private void onBtnClick(ActionEvent actionEvent,int slot)throws IOException {
        Tamagotchi tamagotchi = tamagotchiDB.selectById(slot);
        //TODO faire un choix pour reset la partie ou alors un autre bouton en desssous de chaque parties
        if (tamagotchi == null) {
            toNewTama(actionEvent);
        } else {
            toInGame(actionEvent,tamagotchi);
        }
        //TODO check slot number
    }

    @FXML
    private void slot1Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 1);
    }
    @FXML
    private void slot2Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 2);
    }
    @FXML
    private void slot3Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 3);
    }

    @FXML
    private void returnToSelection(ActionEvent actionEvent) throws IOException{
        isDeathPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewOrLoad.fxml"));
        Pane root = (Pane) loader.load();
        NewOrLoadController newOrLoadController = loader.getController();
        newOrLoadController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
}