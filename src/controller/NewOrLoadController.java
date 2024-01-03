package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Options;
import models.Place;
import models.database.OptionDB;
import models.database.TamagotchiDB;
import models.tamagotchi.Animal;
import models.tamagotchi.Tamagotchi;

public class NewOrLoadController extends AbstractController {
    @FXML
    private ImageView slot1Btn;
    @FXML
    private ImageView slot2Btn;
    @FXML
    private ImageView slot3Btn;
    @FXML
    private Button slot1DeleteButton;
    @FXML
    private Button slot2DeleteButton;
    @FXML
    private Button slot3DeleteButton;
    @FXML
    private Text slot1NameText;
    @FXML
    private Text slot2NameText;
    @FXML
    private Text slot3NameText;
    @FXML
    private Text slot1LastConnexionText;
    @FXML
    private Text slot2LastConnexionText;
    @FXML
    private Text slot3LastConnexionText;
    @FXML
    private Text gamesText;
    @FXML
    private Pane isDeadPane;
    @FXML
    private Text isDeadText;
    @FXML
    private Button returnToSelectionButton;

    private TamagotchiDB tamagotchiDB;
    private OptionDB optionDB;
    private Options options;
    private ResourceBundle resourceBundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tamagotchiDB = new TamagotchiDB();
        optionDB = new OptionDB();
        options = optionDB.select();
        resourceBundle = ResourceBundle.getBundle("resources/language/Text", Locale.forLanguageTag(options.getLanguage()));

        //Setting Text__________________________________________________________
        slot1NameText.setText("");
        slot2NameText.setText("");
        slot3NameText.setText("");
        slot1LastConnexionText.setText("");
        slot2LastConnexionText.setText("");
        slot3LastConnexionText.setText("");
        gamesText.setText(resourceBundle.getString("games") +" :");
        slot1DeleteButton.setText(resourceBundle.getString("deleteMessage") + " 1");
        slot2DeleteButton.setText(resourceBundle.getString("deleteMessage") + " 2");
        slot3DeleteButton.setText(resourceBundle.getString("deleteMessage") + " 3");
        isDeadText.setText(resourceBundle.getString("isDeadMessage"));
        returnToSelectionButton.setText(resourceBundle.getString("return"));

        
        
        
        //Setting Images
        ArrayList<Tamagotchi> selectSlotSaved = tamagotchiDB.selectSlotSaved();
        String path = "";
        for(Tamagotchi tamagotchi : selectSlotSaved){
            String date ="";
            //
            if(options.getLanguage().equals("en")){
                date = tamagotchi.getLastTimeChangedUSFormat();
            }
            else{
                date=tamagotchi.getLastTimeChangedRegularFormat();
            }
            switch (tamagotchi.getClass().getSimpleName()) {
            case "Dog":
                path = "src/resources/tama_sprites/dog.png";
                break;
            case "Cat":
                path = "src/resources/tama_sprites/cat.png";
                break;
            case "Rabbit":
                path = "src/resources/tama_sprites/rabbit.png";
                break;
            case "Robot":
                path = "src/resources/tama_sprites/robot.png";
                break;
            default:
                path = "src/resources/tama_sprites/questionmark.png";
                break;
            }
            try {
                if(tamagotchi.getSlot()==1){
                    slot1Btn.setImage(new Image(new FileInputStream(path)));
                    slot1NameText.setText(resourceBundle.getString("name")+ " : " + tamagotchi.getName());
                    slot1LastConnexionText.setText(resourceBundle.getString("lastConnexion")+" :\n"+date);
                }
                else if(tamagotchi.getSlot() == 2){
                    slot2Btn.setImage(new Image(new FileInputStream(path)));
                    slot2NameText.setText(resourceBundle.getString("name")+ " : " + tamagotchi.getName());
                    slot2LastConnexionText.setText(resourceBundle.getString("lastConnexion")+" :\n"+date);
                }
                else if(tamagotchi.getSlot() == 3){
                    slot3Btn.setImage(new Image(new FileInputStream(path)));
                    slot3NameText.setText(resourceBundle.getString("name") + " : " + tamagotchi.getName());
                    slot3LastConnexionText.setText(resourceBundle.getString("lastConnexion")+" :\n"+date);
                }
            }
            catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
        }


    }
    

    private void setTamagotchiTextToNull(int _slot){
        try{
        if(_slot == 1){
            slot1Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/questionmark.png")));
            slot1NameText.setText("");
            slot1LastConnexionText.setText("");
        }
        else if(_slot == 2){
            slot2Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/questionmark.png")));
            slot2NameText.setText("");
            slot2LastConnexionText.setText("");
        }
        else if(_slot == 3){
            slot3Btn.setImage(new Image(new FileInputStream("src/resources/tama_sprites/questionmark.png")));
            slot3NameText.setText("");
            slot3LastConnexionText.setText("");
        }
        }
        catch(FileNotFoundException e) { System.out.println(e.getMessage()); }
        
    }

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
    private void toNewTama(ActionEvent actionEvent,int slot) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewTama.fxml"));
        Pane root = (Pane) loader.load();
        NewTamaController newTamaController = loader.getController();
        newTamaController.setMusic(music);
        newTamaController.setSlot(slot);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
 
    private void toInGame(ActionEvent actionEvent,Tamagotchi tamagotchi) throws IOException {
        if(tamagotchi.getCurrentHealth() >0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
            Pane root = (Pane) loader.load();
            InGameController inGameController = loader.getController();
            inGameController.initTamagotchi(tamagotchi);
            inGameController.setMusic(music);
            Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root);
        }
        else{
            isDeadPane.setVisible(true);
        }
        }


    private void onBtnClick(ActionEvent actionEvent,int slot)throws IOException {
        Tamagotchi tamagotchi = tamagotchiDB.selectBySlot(slot);
        if (tamagotchi == null) {
            toNewTama(actionEvent,slot);
        } else {
            toInGame(actionEvent,tamagotchi);
        }
        //TODO check slot number
    }
    private void onBtnDelete(ActionEvent actionEvent,int slot)throws IOException {
        tamagotchiDB.delete(slot);
        setTamagotchiTextToNull(slot);
        //Platform.runLater(() -> init());
        
    }

    @FXML
    private void slot1Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 1);
    }
    @FXML
    private void slot2Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 2);
    }
    @FXML
    private void slot3Click(ActionEvent actionEvent) throws IOException {
        onBtnClick(actionEvent, 3);
    }


    @FXML
    private void slot1Delete(ActionEvent actionEvent) throws IOException {
        onBtnDelete(actionEvent, 1);
    }
    @FXML
    private void slot2Delete(ActionEvent actionEvent) throws IOException {
        onBtnDelete(actionEvent, 2);
    }
    @FXML
    private void slot3Delete(ActionEvent actionEvent) throws IOException {
        onBtnDelete(actionEvent, 3);
    }

    @FXML
    private void returnToSelection(ActionEvent actionEvent) throws IOException{
        isDeadPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewOrLoad.fxml"));
        Pane root = (Pane) loader.load();
        NewOrLoadController newOrLoadController = loader.getController();
        newOrLoadController.setMusic(music);
        Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
}