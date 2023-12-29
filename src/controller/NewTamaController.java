package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import models.database.TamagotchiDB;
import models.tamagotchi.Animal;
import models.tamagotchi.Cat;
import models.tamagotchi.Dog;
import models.tamagotchi.Rabbit;
import models.tamagotchi.Robot;
import models.tamagotchi.Tamagotchi;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewTamaController extends AbstractController {
    private PlaceController pController = new PlaceController(true);
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
    private Button bVerif;

    @FXML
    private Pane pConfirmation;

    @FXML
    private Label lNom;

    @FXML
    private Label lType;

    @FXML
    private Label lDifficulte;

    @FXML
    private ChoiceBox cbDifficulte;
    

    @FXML
    private void onVerifClick() {
        boolean isTfNameEmpty = tfName.getText().trim().isEmpty();
        boolean isCbDifficulte = cbDifficulte.getSelectionModel().isEmpty();
        RadioButton selectedRadioButton = (RadioButton) tgType.getSelectedToggle();
        if (isTfNameEmpty) {
            //affiche message "rentrer le nom du tama"
            showAlert("Erreur", "Veuillez entrer le nom du tamagotchi.", AlertType.ERROR);
        } else if (selectedRadioButton == null) {
            //affiche message "rentrer type tama"
            showAlert("Erreur", "Veuillez sélectionner le type du tamagotchi.", AlertType.ERROR);
        } else if (isCbDifficulte) {
            showAlert("Erreur", "Veuillez sélectionner la difficulté de votre partie.", AlertType.ERROR);
        } else {
            // Récupérer la valeur du TextField
            String valeurTextField = tfName.getText();
            lNom.setText("Nom : " + valeurTextField);
            // Récupérer le Radio Button coché
            String typeSelectionne = mapType(selectedRadioButton);
            lType.setText("Type : " + typeSelectionne);
            // Récupérer la difficulté
            String valeurDifficulte = (String) cbDifficulte.getSelectionModel().getSelectedItem();
            lDifficulte.setText("Difficulté : " + valeurDifficulte);
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
        pConfirmation.setVisible(true);
        pConfirmation.toFront();
        tfName.setDisable(true);
        rbChat.setDisable(true);
        rbChien.setDisable(true);
        rbLapin.setDisable(true);
        rbRobot.setDisable(true);
        cbDifficulte.setDisable(true);
    }

    @FXML
    private void onOuiClick(ActionEvent actionEvent) throws IOException {
        int valeurDifficulte = mapDifficulte(cbDifficulte.getSelectionModel().getSelectedItem().toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
        Pane root = (Pane) loader.load();
        InGameController inGameController = loader.getController();
        inGameController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
        /*
         * TODO CHANGE, ONLY FOR TESTING
         */
        // note de A: je teste des trucs
        // String typeSelectionne = mapType((RadioButton) tgType.getSelectedToggle());
        // Tamagotchi testing = null;
        // switch (typeSelectionne) {
        //     case "Chat":
        //         testing = new Cat(tfName.getText(), valeurDifficulte, pController.getPlaces().get(1));
        //         testing.setObserver(inGameController);
        //         testing.startRoutine();
        //         inGameController.initTamagotchi(testing);
        //         TamagotchiDB tamagotchiDBcat = new TamagotchiDB();
        //         tamagotchiDBcat.add((Animal) testing, 1);
        //         break;
        //     case "Chien":
        //         testing = new Dog(tfName.getText(), valeurDifficulte, pController.getPlaces().get(1));
        //         testing.setObserver(inGameController);
        //         testing.startRoutine();
        //         inGameController.initTamagotchi(testing);
        //         TamagotchiDB tamagotchiDBdog = new TamagotchiDB();
        //         tamagotchiDBdog.add((Animal) testing, 1);
        //         break;
        //    case "Lapin":
        //         testing = new Rabbit(tfName.getText(), valeurDifficulte, pController.getPlaces().get(1));
        //         testing.setObserver(inGameController);
        //         testing.startRoutine();
        //         inGameController.initTamagotchi(testing);
        //         TamagotchiDB tamagotchiDBrabbit = new TamagotchiDB();
        //         tamagotchiDBrabbit.add((Animal) testing, 1);
        //         break;
        //     case "Robot":
        //         testing = new Robot(tfName.getText(), valeurDifficulte, pController.getPlaces().get(1));
        //         testing.setObserver(inGameController);
        //         testing.startRoutine();
        //         inGameController.initTamagotchi(testing);
        //         TamagotchiDB tamagotchiDBrobot = new TamagotchiDB();
        //         tamagotchiDBrobot.add((Animal) testing, 1);
        //         break;
        //     default:
        //     // Gestion d'un cas par défaut si nécessaire
        // }
        
        
        // ce qu'il y avait avant, je teste des trucs
        Tamagotchi testing = new Cat(tfName.getText(), 32, pController.getPlaces().get(1));
        testing.setDifficulty(2);
        testing.setObserver(inGameController);
        testing.startRoutine();
        inGameController.initTamagotchi(testing);
        TamagotchiDB tamagotchiDB = new TamagotchiDB();
        tamagotchiDB.add((Animal) testing, 1);
    }
    

    @FXML
    private void onNonClick() {
        pConfirmation.setVisible(false);
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
                typeSelectionne = "Chat";
                break;
            case "rbChien":
                typeSelectionne = "Chien";
                break;
            case "rbLapin":
                typeSelectionne = "Lapin";
                break;
            case "rbRobot":
                typeSelectionne = "Robot";
                break;
            default:
                // Gestion d'un cas par défaut si nécessaire
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
                // Gestion d'un cas par défaut si nécessaire
                return 0; // Valeur par défaut
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbDifficulte.getItems().addAll("Facile", "Normal", "Difficile");
    }
}
