package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
        this.LoadOptions();
    }

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
    private void ToNewOrLoad(ActionEvent actionEvent) throws IOException {
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
    private void ToOptions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Option.fxml"));
        Pane root = (Pane) loader.load();
        OptionController optionController = loader.getController();
        optionController.SetMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    /**
     * 
     * @param actionEvent
     */
    @FXML
    private void QuitGame(ActionEvent actionEvent) {
        Platform.exit();
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

    /**
     * OPTIONS
     */
    private Options options;

    private void LoadOptions() throws FileNotFoundException, IOException, ParseException {
        this.options = new Options();
        JSONParser parser = new JSONParser();
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        Object json = parser.parse(new FileReader(jsonFile));
        JSONObject optionsJson = (JSONObject) ((JSONObject) json).get("options");
        this.options.setVolume((double) optionsJson.get("volume"));
        System.out.println(options.getVolume());
    }
}
