package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import models.Options;
import models.database.OptionDB;

public class MenuController extends AbstractController {
    private Options options;
    private OptionDB optionDB;
    private ResourceBundle resourceBundle;
    @FXML
    private Button playButton;
    @FXML
    private Button optionsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!this.databaseHere()) this.setUpDatabase();
        this.loadOptions();
        playButton.setText(resourceBundle.getString("play").toUpperCase());
        optionsButton.setText(resourceBundle.getString("options").toUpperCase());

    }

    /**
     * 
     */
    @Override
    public void setMusic(MediaView musicView) {
        this.music = musicView;
        this.music.getMediaPlayer().setVolume(this.options.getVolume());
    }
    
    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void toNewOrLoad(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewOrLoad.fxml"));
        Pane root = (Pane) loader.load();
        NewOrLoadController newOrLoadController = loader.getController();
        newOrLoadController.setMusic(music);
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
        optionController.setOptions(options);
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

    /**
     * OPTIONS
     */
    private void loadOptions() {
        optionDB = new OptionDB();
        this.options = optionDB.select();
        resourceBundle = ResourceBundle.getBundle("resources/language/Text", Locale.forLanguageTag(options.getLanguage()));
    }
}
