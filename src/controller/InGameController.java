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
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Place;
import models.Status.EPlace;
import models.Status.MentalState;
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
    private ResourceBundle resourceBundle;
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
    private Label nameLabel;

    @FXML
    private Button rightPlaceButton;
    @FXML
    private Button leftPlaceButton;
    @FXML
    private Text currentPlaceText;
    @FXML
    private Text mentalText;
    @FXML
    private Text weatherText;
    @FXML
    private Button quitButton;
    @FXML
    private Button glossaireButton;
    @FXML
    private Button actionButton;
    @FXML
    private StackPane spDeathPane;
    @FXML
    private AnchorPane backPane;
    @FXML
    private RotateTransition backflipTransition;
    @FXML
    private TranslateTransition upAndDownTrasition;
    @FXML
    private Text healthText;
    @FXML
    private Text energyText;
    @FXML
    private Text cleanlinessText;
    @FXML
    private Text satietyText;
    @FXML
    private Text deathText;


   public void initTamagotchi(Tamagotchi _tamagotchi) {
        this.tamagotchi = _tamagotchi;
        updateAllText();
        statsDisplay();
        nameLabel.setText(resourceBundle.getString("name") +" : "+ tamagotchi.getName());
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
            //init livingroom action animation
            backflipTransition = new RotateTransition();
            backflipTransition.setDuration(javafx.util.Duration.seconds(1));
            backflipTransition.setNode(ivSprite);
            backflipTransition.setFromAngle(0);
            backflipTransition.setToAngle(360);
            backflipTransition.setCycleCount(1);
            //init garden action animation
            upAndDownTrasition = new TranslateTransition();
            upAndDownTrasition.setDuration(javafx.util.Duration.millis(250));
            upAndDownTrasition.setNode(ivSprite);
            upAndDownTrasition.setByY(-50);
            upAndDownTrasition.setCycleCount(4);
            upAndDownTrasition.setAutoReverse(true);

            //Localization
            resourceBundle = ResourceBundle.getBundle("resources/language/Text",Locale.FRENCH);
            //TODO changer en fonction des options
            healthText.setText(resourceBundle.getString("health"));
            energyText.setText(resourceBundle.getString("energy"));
            cleanlinessText.setText(resourceBundle.getString("cleanliness"));
            satietyText.setText(resourceBundle.getString("satiety"));
            quitButton.setText(resourceBundle.getString("quit"));
        }
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

   public void statsDisplay(){
        try{
            stat1.setProgress((double)tamagotchi.getCurrentHealth()/100);
            stat2.setProgress((double)tamagotchi.getCurrentEnergy()/100);
            stat3.setProgress((double)tamagotchi.getCurrentCleaning()/100);
            stat4.setProgress((double)tamagotchi.getCurrentSatiety()/100);
            updateWeatherText();
            updateMental();
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
    
    public void updateMental(){
        mentalText.setText(resourceBundle.getString(tamagotchi.getMentalState().name()));
    }

    public void setPlaceName(Object _object,Place place){
        String room = resourceBundle.getString(place.getCurrentPlace().name());
        if(_object.getClass().getName().equals("javafx.scene.control.Button"))((Button)_object).setText(room);
        else if(_object.getClass().getName().equals("javafx.scene.text.Text")) ((Text)_object).setText(resourceBundle.getString("location")+" : "+ room);
    }

    public void updateWeatherText(){
        weatherText.setText(resourceBundle.getString(tamagotchi.getCurrentPlace().getWeather().name()));
    }

    /**
     * set the text of the action button according to the currentPlace
     */
   public void setActionButtonText(){
        String txt = "";
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
                txt = resourceBundle.getString("sleep");           
                break;
            case LIVINGROOM:
                txt = resourceBundle.getString("yipee");
                break;
            case TOILET:
                txt = resourceBundle.getString("clean");
                break;
            case GARDEN:
                txt = resourceBundle.getString("play");
                break;
            case KITCHEN:
                txt = resourceBundle.getString("eat");
                break;
            default:
                //TODO error
                break;
        }
        actionButton.setText(txt);
   }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
        try {
            if(evt.getPropertyName().equals("statsDisplay")){
                statsDisplay();
            }
            if(evt.getPropertyName().equals("die")){
                afficherPaneDeMort((String)evt.getNewValue());
            }
            if(evt.getPropertyName().equals("enableButtons")){
                enableAll();
            }
            if(evt.getPropertyName().equals("no")){
                no();
            }
        }
        catch (Exception e) {
        // TODO: handle exception
        }
    }



    @FXML
    public void action(ActionEvent event) throws IOException{
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
                bedroomAction();
                break;
            case LIVINGROOM:
                livingRoomAction();
                break;
            case GARDEN:
                gardenAction();
                break;
            case TOILET:
                toiletAction();
                break;
            case KITCHEN:
                kitchenAction();
                break;
            default:
                //TODO error handling
                break;
        }
    }


    private void bedroomAction(){
        //TODO au meme niveau que la musique
        if(!tamagotchi.getSleepRunning().get()){
            if(!tamagotchi.getClass().getSimpleName().equals("Robot"))
            {
                if((((Animal)tamagotchi).getSleepCancel() != 0)){
                    //don't start the sleep routine
                    no();
                    return;
                }
            }
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
    }
    /**
     * DO A BACKFLIP
     */
    private void livingRoomAction(){
        //play a random duration backflip animation
        actionButton.setDisable(true);
        double random = new Random().nextInt(500,1500);
        backflipTransition.setDuration(javafx.util.Duration.millis(random));
        backflipTransition.play();
        sound = new Media(new File("src/resources/sound/goofy_ahh_backflipping.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setRate(((2000-random)/600));
        backflipTransition.setOnFinished(e -> enableAll());
        mediaPlayer.play();
    }
    private void gardenAction(){
        //TODO
        if(tamagotchi.play()){
            //animation
            /*backflipTransition.setDuration(javafx.util.Duration.millis(500)); 
            backflipTransition.setCycleCount(5);
            backflipTransition.setAutoReverse(true);
            backflipTransition.play();
            backflipTransition.setCycleCount(1);
            backflipTransition.setAutoReverse(false);*/
            upAndDownTrasition.play();
        }
    }
    private void toiletAction(){
        //TODO clean sound
        tamagotchi.clean();
    }
    private void kitchenAction(){
        sound = new Media(new File("src/resources/sound/goofy_ahh_eating.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        tamagotchi.eat();
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
        if(tamagotchi.getCurrentHealth() > 0){
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
        }
        

    public void afficherPaneDeMort(String _cause) {
        String deathMessage = "Votre tamagotchi est mort.";
        System.out.println(_cause);
        if(_cause.equals("Suicide")){
            deathMessage = resourceBundle.getString("deathSuicide");
        }
        else if(_cause.equals("Mistreatement")){
            deathMessage = resourceBundle.getString("deathMistreatement");
        }
        spDeathPane.setVisible(true);
        deathText.setText(deathMessage);
        disableAll();
    }

    public void no(){
        sound = new Media(new File("src/resources/sound/goofy_ahh_no.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
