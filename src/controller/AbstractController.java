package controller;

import java.io.File;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import models.database.OptionDB;
import models.database.TamagotchiDB;

public abstract class AbstractController implements Initializable {
    protected MediaView music;

    /**
     * 
     * @param musicView
     */
    public void setMusic(MediaView musicView) {
        this.music = musicView;
    }

    /**
     * 
     * @return
     */
    protected boolean databaseHere() {
        File file = new File("src/resources/data/tamagotchi.db");
        //System.out.println(file.exists());
        return file.exists();
    }

    /**
     * 
     */
    protected void setUpDatabase() {
        TamagotchiDB tamagotchiDB = new TamagotchiDB();
        tamagotchiDB.createTable();
        OptionDB optionDB = new OptionDB();
        optionDB.createTable();
        //optionDB.select();
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
}