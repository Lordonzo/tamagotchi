package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

public class OptionController {
    private MediaView music;

    public void SetMusic(MediaView musicView) {
        this.music = musicView;
        System.out.println(this.music);
    }
    
    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML 
    private void ToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
        Pane root = (Pane) loader.load();
        MenuController menuController = loader.getController();
        menuController.SetMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }


    
    /*
     * CSS THINGS
     */
    @FXML
    private void ChangeImageEntered(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(1);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
    @FXML
    private void ChangeImageExited(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(0);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
}
