package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;



public class ConfirmationNewTamaController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onOuiClick(ActionEvent actionEvent) throws IOException {
        stage.close();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
       Pane root = (Pane) loader.load();
       InGameController inGameController = loader.getController();
       inGameController.setMusic(music);
       Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
       scene.setRoot(root);
    
    }

    @FXML
    private void onNonClick() {
        stage.close();
    }

    private MediaView music;
        public void setMusic(MediaView musicView) {
        this.music = musicView;
    }

    // @FXML 
    // private void toInGame(ActionEvent actionEvent) throws IOException {
    //    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InGame.fxml"));
    //    Pane root = (Pane) loader.load();
    //    InGameController inGameController = loader.getController();
    //    inGameController.setMusic(music);
    //    Scene scene = (Scene) ((Node) actionEvent.getSource()).getScene();
    //    scene.setRoot(root);
    // }
}
