package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewTamaController {
     private MediaView music;
     public void setMusic(MediaView musicView) {
        this.music = musicView;
        
    }

    @FXML 
    private void toNewOrLoad(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewOrLoad.fxml"));
        Pane root = (Pane) loader.load();
        NewOrLoadController newOrLoadController = loader.getController();
        newOrLoadController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
        
     @FXML
    private void changeImageEntered(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(1);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
    @FXML
    private void changeImageExited(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(0);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
    
    @FXML
    private TextField tfName;

    @FXML
    private RadioButton rbChat;

    @FXML
    private RadioButton rbChien;

    @FXML
    private RadioButton rbLapin;

    @FXML
    private RadioButton rbRobot;

    @FXML
    private ToggleGroup tgType;

    @FXML
    private Button bVerif;

    @FXML
    private void onVerifClick() {
        boolean isTfNameEmpty = tfName.getText().trim().isEmpty();
        RadioButton selectedRadioButton = (RadioButton) tgType.getSelectedToggle();
        if (isTfNameEmpty) {
            //affiche message "rentrer le nom du tama"
            showAlert("Erreur", "Veuillez entrer le nom du tama.", AlertType.ERROR);
        } else if (selectedRadioButton == null) {
            //affiche message "rentrer type tama"
            showAlert("Erreur", "Veuillez sélectionner le type du tama.", AlertType.ERROR);
        }
    }

    private void showAlert(String titre, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
