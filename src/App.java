import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

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
        Parent root = FXMLLoader.load(getClass().getResource("view/Menu.fxml"));
        //Pane root = new Pane();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("resources/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
