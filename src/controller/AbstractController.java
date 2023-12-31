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
     * Set up the music
     * @param musicView the music object
     */
    public void setMusic(MediaView musicView) {
        this.music = musicView;
    }

    /**
     * Look up if the file <code>tamagotchi.db</code> exists
     * @return <code>true</code> if it exists. if not <code>false</code>
     */
    protected boolean databaseHere() {
        File file = new File("src/resources/data/tamagotchi.db");
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
        optionDB.select();
    }

    /*
     * CSS THINGS
     */
    @FXML
    protected void changeImageEntered(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(1);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
    @FXML
    protected void changeImageExited(Event event) {
        ColorAdjust cAdjust = new ColorAdjust();
        cAdjust.setBrightness(0);
        ((ImageView)((Node) event.getSource()).lookup(".image-view")).setEffect(cAdjust);
    }
}