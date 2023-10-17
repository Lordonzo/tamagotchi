package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
 
public class View extends Application {
    private Stage currentStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        ImageView lImageView = new ImageView("resources/menu/disconnect.png");
        lImageView.setFitHeight(20);
        lImageView.setFitWidth(20);
        Button leaveButton = new Button("", lImageView);
        leaveButton.getStyleClass().add("icon-button");
        leaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(leaveButton);
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scene.getStylesheets().add("resources/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}