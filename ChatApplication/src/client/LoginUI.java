package client;

import java.awt.Button;
import java.awt.TextField;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI {
    public static void show(Stage stage) {
        TextField username = new TextField();
        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            ChatUI.show(stage, username.getText());
        });

        VBox root = new VBox(10, new Label("Username"), username, loginBtn);
        root.setStyle("-fx-padding:20");

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Login");
        stage.show();
    }
}
