package controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import models.database.OptionDB;

public class ErrorController extends AbstractController{
    @FXML
    private Text errorText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void errorHandle(Exception e){
        try{
            ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/language/Text",Locale.forLanguageTag(new OptionDB().select().getLanguage()));    
            
            errorText.setText(resourceBundle.getString("errorStatement")+e.getMessage()+"\n\n"+resourceBundle.getString("checkFile"));
        }
        catch(Exception exception ){
            errorText.setText("Une erreur c'est produite : "+exception.getMessage()+"\n\nVérifiez les fichiers du jeu");
        }
    }



    /**
     * 
     * @param actionEvent
     */
    @FXML
    private void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
