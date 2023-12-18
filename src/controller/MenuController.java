package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import models.Options;
import models.database.OptionDB;

public class MenuController extends AbstractController {
    private Options options;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!this.databaseHere()) this.setUpDatabase();
        this.loadOptions();
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
        // JSON
        this.options = new Options();
        /*
        JSONParser parser = new JSONParser();
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        Object json;
        try {
            json = parser.parse(new FileReader(jsonFile));
            JSONObject optionsJson = (JSONObject) ((JSONObject) json).get("options");
            this.options.setVolume((double) optionsJson.get("volume"));
        //System.out.println(options.getVolume());
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        } */


        // SQL
        OptionDB optionDB = new OptionDB();
        this.options = optionDB.select();
        //System.out.println(this.options.getVolume() + " " + this.options.getResX() + " " + this.options.getResY());
    }
}
