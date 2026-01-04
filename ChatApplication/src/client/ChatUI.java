package client;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.net.Socket;

public class ChatUI {
    static PrintWriter out;

    public static void show(Stage stage, String username) {
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);

        TextField msgField = new TextField();
        Button sendBtn = new Button("Send");

        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out.println(username);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        chatArea.appendText(msg + "\n");
                    }
                } catch (Exception e) {}
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendBtn.setOnAction(e -> {
            out.println(msgField.getText());
            msgField.clear();
        });

        HBox bottom = new HBox(10, msgField, sendBtn);
        VBox root = new VBox(10, chatArea, bottom);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Chat - " + username);
        stage.show();
    }
}
