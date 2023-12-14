package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

public class InGameController {
    private MediaView music;
    public void setMusic(MediaView musicView) {
       this.music = musicView;
       
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
   //TODO
   private void statsDisplay(){}

    
}
