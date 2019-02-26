package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptNewClient extends Thread {
    private Socket socket;
    private ServerSocket serverSocket;
    private boolean isNewClient = false;

    public AcceptNewClient(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
                System.out.println("New client connected");
                isNewClient = true;

            }
        } catch (IOException e) {
            System.out.println("During creating the socket occurred IO error");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isNewClient() {
        return isNewClient;
    }

    public void setNewClient(boolean newClient) {
        isNewClient = newClient;
    }
}
