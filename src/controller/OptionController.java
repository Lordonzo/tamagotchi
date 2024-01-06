package controller;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private Label languageLabel;
    @FXML
    private Button applyButton;

    private String[] languages = {"fr","en"};
    private OptionDB optionDB;
    private ResourceBundle resourceBundle;
    private String languageCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.optionDB = new OptionDB();
        options = optionDB.select();
        languageCode = options.getLanguage();
        setResourceBundle();
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                Number oldValue,
                Number newValue) {
                volumeValue.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });
        for(int i = 0; i < languages.length;i++){
            languageChoiceBox.getItems().add(i, resourceBundle.getString(languages[i]));
        }
        languageChoiceBox.setValue(resourceBundle.getString(languageCode));
        updateText();
    }

    public void changeLanguage(){
        setLanguageCode(languageChoiceBox.getValue());
        options.setLanguage(languageCode);
        optionDB.update(options);
        setResourceBundle();
        updateText();
    }

    public void setLanguageCode(String _entry){
        if(_entry.equals("Français") ||_entry.equals("French") ) languageCode = "fr";
        else if(_entry.equals("Anglais") || _entry.equals("Anglais") ) languageCode = "en";
    }
    public void updateText(){
        for(int i = 0; i < languages.length;i++){
                languageChoiceBox.getItems().set(i, resourceBundle.getString(languages[i]));
        }
        languageChoiceBox.setValue(resourceBundle.getString(languageCode));
        languageLabel.setText(resourceBundle.getString("language"));
        applyButton.setText(resourceBundle.getString("apply"));
    
    }

    public void setMusic(MediaView musicView) {
        this.music = musicView;
        this.music.getMediaPlayer().setVolume(this.options.getVolume());
    }

    public void setOptions(Options options) {
        this.options = options;
        this.volumeSlider.adjustValue(options.getVolume() * 100);
    }

    public void setResourceBundle(){
        ResourceBundle.clearCache();
        resourceBundle = ResourceBundle.getBundle("resources/language/Text", Locale.forLanguageTag(languageCode));
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


    @FXML
    private void applyOptions() {
        changeLanguage();
        OptionDB optionDB = new OptionDB();
        this.options.setVolume(volumeSlider.getValue()/100);
        optionDB.update(this.options);
        setMusic(this.music);
    }
}
