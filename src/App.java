import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;
import view.MenuFX;

public class App extends Application {
    /*  TODO
     *  - CHANGE SCENES FROM ROOT (NOT SCENE)
     * 
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = MenuFX.MenuScene(primaryStage);
        scene.getStylesheets().add("resources/style.css");
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
