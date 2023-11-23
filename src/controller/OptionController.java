package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import models.Options;

public class OptionController {
    private MediaView music;
    @FXML 
    private Slider volumeSlider;

    public OptionController() throws FileNotFoundException, IOException, ParseException {
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
    private void toMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
        Pane root = (Pane) loader.load();
        MenuController menuController = loader.getController();
        menuController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
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
        if (options == null) this.options = new Options();
        JSONParser parser = new JSONParser();
        File jsonFile = new File(getClass().getResource("../resources/data/options.json").getFile());
        Object json = parser.parse(new FileReader(jsonFile));
        JSONObject optionsJson = (JSONObject) ((JSONObject) json).get("options");
        this.options.setVolume((double) optionsJson.get("volume"));
        System.out.println(options.getVolume());
    }

    @FXML
    private void applyOptions() {
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

        // Apply all changes
        try {
            loadOptions();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        setMusic(this.music);
    }
}
