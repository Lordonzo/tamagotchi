package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuFX {
    public static Scene MenuScene(Stage currentStage) {
        Pane root = new Pane();
        currentStage.setTitle("Hello World");
        ImageView lImageView = new ImageView("resources/menu/disconnect.png");
        lImageView.setFitHeight(30);
        lImageView.setFitWidth(30);
        Button createGameButton = new Button("", lImageView);
        createGameButton.getStyleClass().add("icon-button");
        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentStage.getScene().setRoot(TestScene(currentStage));
            }
        });
        root.getChildren().add(createGameButton);
        return new Scene(root);
    }

    private static Parent TestScene(Stage currentStage) {
        Pane root = new Pane();
        ImageView lImageView = new ImageView("resources/menu/disconnect.png");
        lImageView.setFitHeight(30);
        lImageView.setFitWidth(30);
        Button createGameButton = new Button("", lImageView);
        createGameButton.getStyleClass().add("icon-button");
        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentStage.close();
            }
        });
        root.getChildren().add(createGameButton);
        return root;
    }
}
