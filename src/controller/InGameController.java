package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import models.Options;
import models.Place;
import models.Status.MentalState;
import models.database.OptionDB;
import models.database.TamagotchiDB;
import models.tamagotchi.Tamagotchi;

public class InGameController extends AbstractController implements PropertyChangeListener {
    private Tamagotchi tamagotchi;

    private Media sound;
    private MediaPlayer mediaPlayer;
    private ResourceBundle resourceBundle;
    private TamagotchiDB tamagotchiDB;
    private OptionDB optionDB;
    private Options options;
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
    private Text weightText;
    @FXML
    private Button quitButton;
    @FXML
    private Button tutoButton;
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
    @FXML
    private Button toMenuButton;

    @FXML
    private Pane tutoPane;
    @FXML
    private Text tutoIntroText;
    @FXML
    private Text tutoText;
    @FXML
    private Button returnToGameButton;


   public void initTamagotchi(Tamagotchi _tamagotchi) {
        this.tamagotchi = _tamagotchi;
        tamagotchi.setObserver(this);
        tamagotchi.startRoutine();
        ivSprite.setImage(tamagotchi.getImage());
        //setting UI text
        nameLabel.setText(resourceBundle.getString("name") +" : "+ tamagotchi.getName());
        
        updatePlaceText();
        healthText.setText(resourceBundle.getString(tamagotchi.stat1String));
        energyText.setText(resourceBundle.getString(tamagotchi.stat2String));
        cleanlinessText.setText(resourceBundle.getString(tamagotchi.stat3String));
        satietyText.setText(resourceBundle.getString(tamagotchi.stat4String));
        quitButton.setText(resourceBundle.getString("quit"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Database
        tamagotchiDB = new TamagotchiDB();
        
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
        optionDB = new OptionDB();
        options = optionDB.select();
        resourceBundle = ResourceBundle.getBundle("resources/language/Text",Locale.forLanguageTag(options.getLanguage())); //TODO avec les options

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

   public void rightPlace(ActionEvent actionEvent) throws IOException{
        tamagotchi.goToRightPlace();
        updatePlaceText();
    }
   public void leftRoom(ActionEvent actionEvent) throws IOException{
        tamagotchi.goToLeftPlace();
        updatePlaceText();
    }

    public void updatePlaceText(){
        setActionButtonText();
        setPlaceName(rightPlaceButton,tamagotchi.getCurrentPlace().getNextPlace());
        setPlaceName(leftPlaceButton,tamagotchi.getCurrentPlace().getPreviousPlace());
        setPlaceName(currentPlaceText,tamagotchi.getCurrentPlace());
    }
    
    public void updateMental(MentalState _newMentalState){
        mentalText.setText(resourceBundle.getString(_newMentalState.name()));
    }

    public void setPlaceName(Object _object,Place place){
        String room = resourceBundle.getString(place.getCurrentPlace().name());
        if(_object.getClass().getName().equals("javafx.scene.control.Button"))((Button)_object).setText(room);
        else if(_object.getClass().getName().equals("javafx.scene.text.Text")) ((Text)_object).setText(resourceBundle.getString("location")+" : "+ room);
    }

    public void updateWeather(){
        weatherText.setText(resourceBundle.getString(tamagotchi.getCurrentPlace().getWeather().name()));
    }

    /**
     * set the text of the action button according to the currentPlace
     */
   public void setActionButtonText(){
        String txt = "";
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
                txt = resourceBundle.getString(tamagotchi.bedRoomActionString);           
                break;
            case LIVINGROOM:
                txt = resourceBundle.getString(tamagotchi.livingroomActionString);
                break;
            case TOILET:
                txt = resourceBundle.getString(tamagotchi.toiletActionString);
                break;
            case GARDEN:
                txt = resourceBundle.getString(tamagotchi.gardenActionString);
                break;
            case KITCHEN:
                txt = resourceBundle.getString(tamagotchi.kitchenActionString);
                break;
            default:
                //TODO error
                break;
        }
        actionButton.setText(txt);
   }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            switch (evt.getPropertyName()) {
                case "die":
                    showDeathPane((String)evt.getNewValue());
                    break;
                case "enableButtons":
                    enableAll();
                    break;
                case "no":
                    no();
                    break;
                case "bedroomActionPrep":
                    bedRoomActionPrep();
                    break;
                case "stopBedroomActionPrep":
                    stopBedRoomActionPrep();
                    break;
                case "updatePlaceText":
                    Platform.runLater(()->updatePlaceText());
                    break;
                case "livingroomActionPrep":
                    livingroomActionPrep();
                    break;
                case "gardenActionPrep":
                    gardenActionPrep();
                    break;
                case "kitchenActionPrep":
                    kitchenActionPrep();
                    break;
                case "toiletActionPrep":
                    toiletActionPrep();
                    break;
                case "updateStat1":
                    updateStat1((int)evt.getNewValue());
                    break;
                case "updateStat2":
                    updateStat2((int)evt.getNewValue());
                    break;
                case "updateStat3":
                    updateStat3((int)evt.getNewValue());
                    break;
                case "updateStat4":
                    updateStat4((int)evt.getNewValue());
                    break;
                case "updateWeather":
                    updateWeather();
                    break;
                case "updateMental":
                    updateMental((MentalState)evt.getNewValue());
                    break;
                case "updateWeight":
                    updateWeight();
                    break;
                case "saveGame":
                    save();
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
        }
    }



    private void updateWeight() {
        weightText.setText(resourceBundle.getString("weight") +" : " +tamagotchi.getCurrentWeight() );
    }

    private void updateStat1(int _newValue) {
        stat1.setProgress((double)_newValue/100);
    }
    private void updateStat2(int _newValue) {
        stat2.setProgress((double)_newValue/100);
    }
    private void updateStat3(int _newValue) {
        stat3.setProgress((double)_newValue/100);
    }
    private void updateStat4(int _newValue) {
        stat4.setProgress((double)_newValue/100);
    }
    private void bedRoomActionPrep(){
            sound = tamagotchi.makeBedroomSound();
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            disableAll();
            Platform.runLater(() -> actionButton.setText(resourceBundle.getString(tamagotchi.bedRoomActionStopString)));
            actionButton.setDisable(false);
        
    }
    private void stopBedRoomActionPrep(){
            sound = tamagotchi.makeStopBedroomSound();
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            Platform.runLater(() -> enableAll());
            Platform.runLater(() -> updatePlaceText());

        
    }

    private void livingroomActionPrep(){
        disableAll();
        double random = new Random().nextInt(500,1500);
        backflipTransition.setDuration(javafx.util.Duration.millis(random));
        backflipTransition.setNode(ivSprite);
        backflipTransition.play();
        sound = tamagotchi.makeLivingroomSound();
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setRate(((2000-random)/600));
        backflipTransition.setOnFinished(e -> enableAll());
        mediaPlayer.play();
    }

    private void gardenActionPrep(){
        sound = tamagotchi.makeGardenSound();
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        upAndDownTrasition.play();
    }

    private void kitchenActionPrep(){
        sound = tamagotchi.makeKitchenSound();
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void toiletActionPrep(){
        sound = tamagotchi.makeToiletSound();
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML
    public void action(ActionEvent event) throws IOException{
        switch (tamagotchi.getCurrentPlace().getCurrentPlace()) {
            case BEDROOM:
                tamagotchi.bedroomAction();
                break;
            case LIVINGROOM:
                tamagotchi.livingroomAction();
                break;
            case GARDEN:
                tamagotchi.gardenAction();
                break;
            case TOILET:
                tamagotchi.toiletAction();
                break;
            case KITCHEN:
                tamagotchi.kitchenAction();
                break;
            default:
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
        tutoButton.setDisable(true);
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
            tutoButton.setDisable(false);
            actionButton.setDisable(false);
            rightPlaceButton.setDisable(false);
            leftPlaceButton.setDisable(false);
        }
    }
        

    private void showDeathPane(String _cause) {
        String deathMessage = "";
        if(_cause.equals("Suicide")){
            deathMessage = resourceBundle.getString("deathSuicide");
        }
        else if(_cause.equals("Mistreatement")){
            deathMessage = resourceBundle.getString("deathMistreatement");
        }
        switch(_cause){
            case "Suicide":
                deathMessage=resourceBundle.getString("deathSuicide");
                break;
            case "Mistreatment":
                deathMessage = resourceBundle.getString("deathMistreatement");
                break;
            default:
                break;

        }
        spDeathPane.setVisible(true);
        deathText.setText(deathMessage);
        toMenuButton.setText(resourceBundle.getString("toMenu"));
        disableAll();
    }

    private void no(){
        sound = new Media(new File("src/resources/sound/goofy_ahh_no.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML
    private void toTuto(ActionEvent event)throws IOException{
        tamagotchi.stopRoutine();
        tutoPane.setVisible(true);
        tutoIntroText.setText(resourceBundle.getString("tutoIntro1")+'\n'+resourceBundle.getString("tutoIntro2")+'\n'+resourceBundle.getString("tutoIntro3")+'\n');
        String text = "";
        if(tamagotchi.getClass().getSimpleName().equals("Robot")){
            text =resourceBundle.getString("tutoAction1Robot")+'\n'+resourceBundle.getString("tutoAction2")+'\n'+resourceBundle.getString("tutoAction3Robot")+'\n'+
                  resourceBundle.getString("tutoSpecialRobot")+'\n'+resourceBundle.getString("tutoAction4")+'\n'+resourceBundle.getString("tutoAction5")+'\n';
        }
        else{
            text =resourceBundle.getString("tutoAction1")+'\n'+resourceBundle.getString("tutoAction2")+'\n'+resourceBundle.getString("tutoAction3")+'\n'+
                  resourceBundle.getString("tutoAction4")+'\n'+resourceBundle.getString("tutoAction5")+'\n';
        }
        text+=resourceBundle.getString("tutoHealth")+'\n'+resourceBundle.getString("tutoMental");
        tutoText.setText(text);
        returnToGameButton.setText(resourceBundle.getString("return"));
    }

    @FXML
    private void returnToGame(ActionEvent event)throws IOException{
        tutoPane.setVisible(false);
        tamagotchi.startRoutine();
    }


    private void save(){
        tamagotchiDB.save(tamagotchi);
    }

}
