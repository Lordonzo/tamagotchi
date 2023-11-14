package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController implements Initializable {
    
    /** 
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }
    
    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void ToNewOrLoad(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/NewOrLoad.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void ToOptions(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Option.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 
     * @param actionEvent
     * @throws IOException
     */
    @FXML 
    private void ToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
    private ImageView quitImg;

    @FXML
    private void ChangeImageEntered() {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(1);
        quitImg.setEffect(cAdjust);
    }
    @FXML
    private void ChangeImageExited() {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(0);
        quitImg.setEffect(cAdjust);
    }
}
