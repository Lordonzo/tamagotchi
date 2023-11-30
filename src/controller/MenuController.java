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
import models.Options;

public class MenuController {
    private MediaView music;

    public MenuController() throws FileNotFoundException, IOException, ParseException {
        this.loadOptions();
    }

    public void setMusic(MediaView musicView) {
        this.music = musicView;
        this.music.getMediaPlayer().setVolume(this.options.getVolume());
        System.out.println(this.music);
    }
    
    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void toNewOrLoad(ActionEvent actionEvent) throws IOException {
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("../view/NewOrLoad.fxml"));
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void toOptions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Option.fxml"));
        Pane root = (Pane) loader.load();
        OptionController optionController = loader.getController();
        optionController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    /**
     * 
     * @param actionEvent
     */
    @FXML
    private void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }


    /*
     * CSS THINGS
     */
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

    /**
     * OPTIONS
     */
    private Options options;

    private void loadOptions() throws FileNotFoundException, IOException, ParseException {
        this.options = new Options();
        JSONParser parser = new JSONParser();
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        Object json = parser.parse(new FileReader(jsonFile));
        JSONObject optionsJson = (JSONObject) ((JSONObject) json).get("options");
        this.options.setVolume((double) optionsJson.get("volume"));
        System.out.println(options.getVolume());
    }
}
