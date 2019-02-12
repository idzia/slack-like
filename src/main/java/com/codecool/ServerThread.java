package com.codecool;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private BufferedReader reader;
    private PrintWriter writer;
    private String text;
    private boolean hasMessage = false;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        try {
            connectionEstablishing();

            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            while (true) {
                if ((text = reader.readLine())!= null) {
                    hasMessage = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionEstablishing() throws IOException {
        output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        writer.println("You joined to #general");
    }
    public void sendMessage(String message){
        writer.println(message);
    }

    public boolean checkHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public String getText() {
        return text;
    }

}



