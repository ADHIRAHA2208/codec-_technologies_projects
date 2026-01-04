package server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler extends Thread {
    private static Set<ClientHandler> clients = new HashSet<>();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            username = in.readLine();
            clients.add(this);

            broadcast("🟢 " + username + " joined the chat");

            String msg;
            while ((msg = in.readLine()) != null) {
                broadcast(username + ": " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clients.remove(this);
        }
    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.out.println(message);
        }
    }
}
