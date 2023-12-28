package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Place;
import models.Status.EPlace;
import models.database.PlaceDB;
import models.database.TamagotchiDB;
import models.tamagotchi.Animal;
import models.tamagotchi.Cat;
import models.tamagotchi.Tamagotchi;
import javafx.animation.RotateTransition;

public class InGameController extends AbstractController implements PropertyChangeListener {
    private Tamagotchi tamagotchi;

    private Media sound;
    private MediaPlayer mediaPlayer;
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
    //Satiety
    private ProgressBar stat4;

    @FXML  
    private ImageView ivSprite;

    @FXML
    private Label idLabel;

    @FXML
    private Button rightPlaceButton;
    @FXML
    private Button leftPlaceButton;
    @FXML
    private Text currentPlaceText;
    @FXML
    private Button quitButton;
    @FXML
    private Button glossaireButton;
    @FXML
    private Button actionButton;
    @FXML
    private StackPane spDeathPane;
    @FXML
    private RotateTransition backflipTransition;


   public void initTamagotchi(Tamagotchi _tamagotchi) {
        this.tamagotchi = _tamagotchi;
        updateAllText();
    }

   @FXML 
   private void toMenu(ActionEvent actionEvent) throws IOException {
       tamagotchi.setCloseGame(true);
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
       Pane root = (Pane) loader.load();
       MenuController menuController = loader.getController();
       menuController.setMusic(music);
       Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
       scene.setRoot(root);
   }

   @FXML
   public void statsDisplay() throws IOException{
            try{
                System.out.println("statsDisplay : " + tamagotchi.getCurrentSatiety());

                stat1.setProgress((double)tamagotchi.getCurrentHealth()/100);
                stat2.setProgress((double)tamagotchi.getCurrentEnergy()/100);
                stat3.setProgress((double)tamagotchi.getCurrentCleaning()/100);
                stat4.setProgress((double)tamagotchi.getCurrentSatiety()/100);

            }
            catch(Exception e){
                e.printStackTrace();
            }
    }

   public void rightPlace(ActionEvent actionEvent) throws IOException{
        tamagotchi.goToRightPlace();
        updateAllText();
    }
   public void leftRoom(ActionEvent actionEvent) throws IOException{
        tamagotchi.goToLeftPlace();
        updateAllText();
    }

    public void updateAllText(){
        setActionButtonText();
        setPlaceName(rightPlaceButton,tamagotchi.getCurrentPlace().getNextPlace());
        setPlaceName(leftPlaceButton,tamagotchi.getCurrentPlace().getPreviousPlace());
        setPlaceName(currentPlaceText,tamagotchi.getCurrentPlace());
    }

    public void setPlaceName(Object _button,Place place){
        String current ="";
        switch (place.getCurrentPlace()) {
            case BEDROOM:
                current = "Chambre";         
                break;
            case LIVINGROOM:
                current = "Salon";
                break;
            case TOILET:
                current = "Salle de bain";
                break;
            case GARDEN:
                current = "Jardin";
                break;
            case KITCHEN:
                current = "Cuisine";
                break;
            default:
                //TODO error
                break;
        }

       if(_button.getClass().getName().equals("javafx.scene.control.Button"))((Button)_button).setText(current);
        else ((Text)_button).setText(current);
    }
    /**
     * set the text of the action button according to the currentPlace
     */
   public void setActionButtonText(){
        String txt = "";
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
                txt = "Dormir";            
                break;
            case LIVINGROOM:
                txt = "Youpi !!"; //TODO changer j'ai pas d'idée
                break;
            case TOILET:
                txt = "Nettoyer";
                break;
            case GARDEN:
                txt = "Jouer";
                break;
            case KITCHEN:
                txt = "Manger";
                break;
            default:
                //TODO error
                break;
        }
        actionButton.setText(txt);
   }

    public void setNameLabel(){
        if(tamagotchi != null){
            idLabel.setText("Nom : "+ tamagotchi.getName());
        }
        else{
            //TODO FAIRE UN TRUC POUR LES ERREURS
            System.out.println("Erreur du chargement du tamagotchi");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
        try {
            if(evt.getPropertyName().equals("statsDisplay")){
                statsDisplay();
            }
            if(evt.getPropertyName().equals("die")){
                afficherPaneDeMort();
            }
            if(evt.getPropertyName().equals("enableButtons")){
                enableAll();
            }
        }
        catch (Exception e) {
        // TODO: handle exception
        }
    }
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
                try {
                    ivSprite.setImage(new Image(new FileInputStream("src/resources/tama_sprites/dog.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            case "Cat":
                System.out.println(ivSprite.getImage());
                try {
                    ivSprite.setImage(new Image(new FileInputStream("src/resources/tama_sprites/cat.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            case "Rabbit":
                System.out.println(ivSprite.getImage());
                try {
                    ivSprite.setImage(new Image(new FileInputStream("src/resources/tama_sprites/rabbit.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            case "Robot":
                System.out.println(ivSprite.getImage());
                try {
                    ivSprite.setImage(new Image(new FileInputStream("src/resources/tama_sprites/robot.png")));
                } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
                break;
            }
            //init Rotation animation
            backflipTransition = new RotateTransition();
            backflipTransition.setDuration(javafx.util.Duration.seconds(1));
            backflipTransition.setNode(ivSprite);
            backflipTransition.setFromAngle(0);
            backflipTransition.setToAngle(360);
            backflipTransition.setCycleCount(1);
        }
    }


    @FXML
    public void action(ActionEvent event) throws IOException{
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
            if(!tamagotchi.getSleepRunning().get()){
                disableAll();
                actionButton.setDisable(false);
                sound = new Media(new File("src/resources/sound/goofy_ahh_sleeping.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                tamagotchi.startSleep();
            }
            else{
                tamagotchi.setSleepRunning(false);
            }
                //TODO au meme niveau que la musique
                //TODO soundEating en parametre de ingamecontroller et mettre des differents sound pour chaque tama
                break;
            case LIVINGROOM:
            //DO A BACKFLIP
                double random = new Random().nextInt(100,2000);
                backflipTransition.setDuration(javafx.util.Duration.millis(random));
                backflipTransition.play();
                sound = new Media(new File("src/resources/sound/goofy_ahh_backflipping.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setRate((2000-random)/600);
                mediaPlayer.play();
                break;
            case GARDEN:
            //TODO
                break;
            case TOILET:
                //TODO clean sound
                tamagotchi.clean();
                break;
            case KITCHEN:
                sound = new Media(new File("src/resources/sound/goofy_ahh_eating.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                tamagotchi.eat();
                break;
            default:
                //TODO error handling
                break;
        }
    }

    @FXML
    private void disableAll() {
        stat1.setDisable(true);
        stat2.setDisable(true);
        stat3.setDisable(true);
        stat4.setDisable(true);
        quitButton.setDisable(true);
        glossaireButton.setDisable(true);
        actionButton.setDisable(true);
        rightPlaceButton.setDisable(true);
        leftPlaceButton.setDisable(true);
    }

    @FXML
    private void enableAll(){
        stat1.setDisable(false);
        stat2.setDisable(false);
        stat3.setDisable(false);
        stat4.setDisable(false);
        quitButton.setDisable(false);
        glossaireButton.setDisable(false);
        actionButton.setDisable(false);
        rightPlaceButton.setDisable(false);
        leftPlaceButton.setDisable(false);
    }

    public void afficherPaneDeMort() {
        spDeathPane.setVisible(true);
        disableAll();
    }

}
