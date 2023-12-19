package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EventListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.tamagotchi.Tamagotchi;

public class InGameController implements PropertyChangeListener {
    private MediaView music;
    private Tamagotchi tamagotchi;
    @FXML
    //Health
    private ProgressBar stat1;
    @FXML
    //Energy
    private ProgressBar stat2;
    @FXML
    //Cleaning
    private ProgressBar stat3;

    @FXML
    private Button rightPlaceButton;
    public void setMusic(MediaView musicView) {
       this.music = musicView;

       
   }
   public void setTamagotchi(Tamagotchi _tamagotchi) {
        //TODO je sens ca va faire des problème ca, faire un constructeur par copie
       this.tamagotchi = _tamagotchi;
       
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
   public void statsDisplay() throws IOException{
            try{
                System.out.println((double)tamagotchi.getCurrentHealth()/100);
                System.out.println(stat1.getId());
                /*Platform.runLater(() -> stat1.setProgress((double)tamagotchi.getCurrentHealth()/100));
                Platform.runLater(() -> stat2.setProgress(0.2));*/
                stat1.setProgress((double)tamagotchi.getCurrentHealth()/100);
                stat2.setProgress((double)tamagotchi.getCurrentEnergy()/100);
                stat3.setProgress((double)tamagotchi.getCurrentCleaning()/100);
            }
            catch(Exception e){
                e.printStackTrace();
            }


        }


   //TODO
   public void rightPlace(ActionEvent actionEvent) throws IOException{
            //rightPlaceButton.setText("dfghj,k;");
   }
   //TODO
   public void leftRoom(ActionEvent actionEvent) throws IOException{
    
   }

@Override
public void propertyChange(PropertyChangeEvent evt) {
    try {
        statsDisplay();}

    catch (Exception e) {
        // TODO: handle exception
    }
}
}
