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

    // TODO AFFICHER QUAND TU REVIENS
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlaceDB placeDB = new PlaceDB();
        ArrayList<Place> places = placeDB.select();
        TamagotchiDB tamagotchiDB = new TamagotchiDB();
        ArrayList<Tamagotchi> selectSlotSaved = tamagotchiDB.selectSlotSaved(places);
        System.out.println(selectSlotSaved);
        if (!selectSlotSaved.isEmpty()) {
            System.out.println(selectSlotSaved.get(0).getName());
            switch (selectSlotSaved.get(0).getClass().getSimpleName()) {
            case "Dog":
                //System.out.println(slot1Btn.getImage());
                try {
                    slot1Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/dog.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            case "Cat":
                System.out.println(slot1Btn.getImage());
                try {
                    slot1Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/cat.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            case "Rabbit":
                System.out.println(slot1Btn.getImage());
                try {
                    slot1Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/rabbit.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
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
}
