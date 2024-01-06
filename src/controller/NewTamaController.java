package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Options;
import models.Place;
import models.database.OptionDB;
import models.database.TamagotchiDB;
import models.tamagotchi.Cat;
import models.tamagotchi.Dog;
import models.tamagotchi.Rabbit;
import models.tamagotchi.Robot;
import models.tamagotchi.Tamagotchi;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewTamaController extends AbstractController {
    private PlaceController pController = new PlaceController();

    private int slot;
    private String type;
    private String difficulty;
    private OptionDB optionDB;
    private Options options;
    private ResourceBundle resourceBundle;
    @FXML
    private TextField tfName;

    @FXML
    private RadioButton rbChat;

    @FXML
    private RadioButton rbChien;

    @FXML
    private RadioButton rbLapin;

    @FXML
    private RadioButton rbRobot;

    @FXML
    private ToggleGroup tgType;

    @FXML
    private Button returnButton;

    @FXML
    private Pane confirmPane;
    @FXML
    private Button confirmPaneYesButton;
    @FXML
    private Button confirmPaneNoButton;


    @FXML
    private Label lNom;

    @FXML
    private Label lType;

    @FXML
    private Label lDifficulte;

    @FXML
    private ChoiceBox<String> cbDifficulte;
    @FXML
    private Text difficultyText;
    @FXML
    private Text typeText;
    @FXML
    private Button confirmButton;
    @FXML
    private Text nameText;
    @FXML
    private Text confirmPaneText;
    
    @FXML 
    private void toNewOrLoad(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewOrLoad.fxml"));
        Pane root = (Pane) loader.load();
        NewOrLoadController newOrLoadController = loader.getController();
        newOrLoadController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML 
    private void toInGame(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
        Pane root = (Pane) loader.load();
        InGameController inGameController = loader.getController();
        inGameController.setMusic(music);

        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }


    @FXML
    private void onVerifClick() {
        boolean isTfNameEmpty = tfName.getText().trim().isEmpty();
        boolean isCbDifficulte = cbDifficulte.getSelectionModel().isEmpty();
        RadioButton selectedRadioButton = (RadioButton) tgType.getSelectedToggle();
        if (isTfNameEmpty) {
            //affiche message "rentrer le nom du tama"
            showAlert(resourceBundle.getString("error"), resourceBundle.getString("enterName"), AlertType.ERROR);
        } else if (selectedRadioButton == null) {
            //affiche message "rentrer type tama"
            showAlert(resourceBundle.getString("error"), resourceBundle.getString("enterType"), AlertType.ERROR);
        } else if (isCbDifficulte) {
            showAlert(resourceBundle.getString("error"), resourceBundle.getString("enterDifficulty"), AlertType.ERROR);
        }else if(tfName.getText().length() > 25){
            //affiche message "moins de 25 characters"
            showAlert(resourceBundle.getString("error"),resourceBundle.getString("lessChar"), AlertType.ERROR);
        } else {
            // Récupérer la valeur du TextField
            String valeurTextField = tfName.getText();
            lNom.setText(resourceBundle.getString("name")+" : " + valeurTextField);
            // Récupérer le Radio Button coché
            type = mapType(selectedRadioButton);
            lType.setText(resourceBundle.getString("type")+" : " + type);
            // Récupérer la difficulté
            difficulty = (String) cbDifficulte.getSelectionModel().getSelectedItem();
            lDifficulte.setText(resourceBundle.getString("difficulty")+" : " + difficulty);
            showConfirmationPane();
        }
    }

    private void showAlert(String titre, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationPane() {
        confirmPane.setVisible(true);
        confirmPane.toFront();
        tfName.setDisable(true);
        rbChat.setDisable(true);
        rbChien.setDisable(true);
        rbLapin.setDisable(true);
        rbRobot.setDisable(true);
        cbDifficulte.setDisable(true);
    }

    @FXML
    private void onOuiClick(ActionEvent actionEvent) throws IOException {
        Tamagotchi tamagotchi = createTamagotchi(tfName.getText(),slot,type, difficulty, pController.getPlaces().get(1),slot);
        TamagotchiDB tamagotchiDB = new TamagotchiDB();
        tamagotchiDB.save(tamagotchi);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
        Pane root = (Pane) loader.load();
        InGameController inGameController = loader.getController();
        inGameController.setMusic(music);
        inGameController.initTamagotchi(tamagotchi);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);

        
        
    }
    
    public void setSlot(int slot) {
        this.slot = slot;
    }
    @FXML
    private void onNonClick() {
        confirmPane.setVisible(false);
        tfName.setDisable(false);
        rbChat.setDisable(false);
        rbChien.setDisable(false);
        rbLapin.setDisable(false);
        rbRobot.setDisable(false);
        cbDifficulte.setDisable(false);
    }

    private String mapType(RadioButton radioButton) {
        String typeSelectionne = "";
    
        switch (radioButton.getId()) {
            case "rbChat":
                typeSelectionne = "Cat";
                break;
            case "rbChien":
                typeSelectionne = "Dog";
                break;
            case "rbLapin":
                typeSelectionne = "Rabbit";
                break;
            case "rbRobot":
                typeSelectionne = "Robot";
                break;
            default:
        }
    
        return typeSelectionne;
    }

    private int mapDifficulte(String difficulte) {
        switch (difficulte) {
            case "Facile":
                return 1;
            case "Normal":
                return 2;
            case "Difficile":
                return 3;
            default:
                return 0;
        }
    }

    private Tamagotchi createTamagotchi(String _name,int _id,String type,String _difficulty,Place place,int _slot){
        Tamagotchi tamagotchi;
        switch (type) {
            case "Cat":
                tamagotchi = new Cat(_name,place,mapDifficulte(_difficulty));
                break;
            case "Dog":
                tamagotchi = new Dog(_name, place,mapDifficulte(_difficulty));
                break;
            case "Rabbit":
                tamagotchi = new Rabbit(_name, place,mapDifficulte(_difficulty));
                break;
            case "Robot":
                tamagotchi = new Robot(_name, place,mapDifficulte(_difficulty));
                break;
            default:
                tamagotchi = new Cat(_name,place,mapDifficulte(_difficulty));
                break;
        }
        tamagotchi.setId(_id);
        tamagotchi.setSlot(_slot);
        return tamagotchi;
        
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optionDB = new OptionDB();
        options = optionDB.select();
        resourceBundle = ResourceBundle.getBundle("resources/language/Text", Locale.forLanguageTag(options.getLanguage()));
        difficultyText.setText(resourceBundle.getString("difficulty")+" :");
        returnButton.setText(resourceBundle.getString("return"));
        confirmButton.setText(resourceBundle.getString("confirm"));
        cbDifficulte.getItems().addAll(resourceBundle.getString("easy"), resourceBundle.getString("normal"), resourceBundle.getString("hard")); //TODO resourcesBundle
        nameText.setText(resourceBundle.getString("name")+" :");
        typeText.setText(resourceBundle.getString("type"));
        confirmPaneNoButton.setText(resourceBundle.getString("no"));
        confirmPaneYesButton.setText(resourceBundle.getString("yes"));
        confirmPaneText.setText(resourceBundle.getString("confirmStartGame"));
    }
}
