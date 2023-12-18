package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import models.Options;
import models.database.OptionDB;

public class OptionController extends AbstractController {
    private Options options;
    @FXML 
    private Slider volumeSlider;
    @FXML
    private Label volumeValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                Number oldValue,
                Number newValue) {
                volumeValue.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });
    }

    public void setMusic(MediaView musicView) {
        this.music = musicView;
        this.music.getMediaPlayer().setVolume(this.options.getVolume());
        //System.out.println(this.music);
    }

    public void setOptions(Options options) {
        this.options = options;
        this.volumeSlider.adjustValue(options.getVolume() * 100);
    }
    
    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML 
    private void toMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
        Pane root = (Pane) loader.load();
        MenuController menuController = loader.getController();
        menuController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    /**
     * OPTIONS
     */
    private void loadOptions() throws FileNotFoundException, IOException, ParseException {
        /* 
        if (options == null) this.options = new Options();
        JSONParser parser = new JSONParser();
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        Object json = parser.parse(new FileReader(jsonFile));
        JSONObject optionsJson = (JSONObject) ((JSONObject) json).get("options");
        this.options.setVolume((double) optionsJson.get("volume"));
        this.volumeSlider.adjustValue((double) optionsJson.get("volume") * 100);
        */
        //System.out.println((double) optionsJson.get("volume"));
        //System.out.println(volumeSlider.valueProperty());
        //System.out.println(options.getVolume());
    }

    @FXML
    private void applyOptions() {
        // JSON
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        JSONObject obj = new JSONObject();
        obj.put("volume", volumeSlider.getValue()/100);
        JSONObject options = new JSONObject();
        options.put("options", obj);
        System.out.println(options.toString());
        try (FileWriter data = new FileWriter(jsonFile)) {
            data.write(options.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionDB optionDB = new OptionDB();
        this.options.setVolume(volumeSlider.getValue()/100);
        // TODO options supplémentaires
        optionDB.update(this.options);


        // Apply all changes
        try {
            loadOptions();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        setMusic(this.music);
    }
}
