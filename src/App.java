import java.io.IOException;

import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;

public class App extends Application {
    // TODO Comment methods for doc


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Menu.fxml"));
        Pane root = (Pane) loader.load();
        MenuController menuController = loader.getController();
        menuController.setMusic(setMusic());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/css/style.css");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private MediaView setMusic() throws IOException {
        MediaView music = new MediaView(new MediaPlayer(new Media(getClass().getResource("resources/music/soul-worker-login-music.mp3").toString())));
        music.getMediaPlayer().setAutoPlay(true);
        music.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        return music;
    }
}
