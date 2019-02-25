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
    private Message message;
    private String text;
    private ObjectInputStream incomingMessage;
    private ObjectOutputStream preparedMessage;
    private boolean hasMessage = false;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        try {
            output = socket.getOutputStream();
//            connectionEstablishing();

            preparedMessage = new ObjectOutputStream(output);

            input = socket.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(input));
            incomingMessage = new ObjectInputStream(input);

            while (true) {
//                if ((text = reader.readLine())!= null) {
//                    hasMessage = true;
//                }
                if (incomingMessage != null) {
                    message = (Message) incomingMessage.readObject();
                    hasMessage = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("I can not found appropriate class");
        }
    }

    private void connectionEstablishing() throws IOException {
        writer = new PrintWriter(output, true);
        writer.println("You joined to #general");

    }
//    public void sendMessage(String message){
    public void sendMessage(Message message){
        try {
        preparedMessage.writeObject(message);
        } catch (IOException e) {
            System.out.println("exception outputHandler");
        }

//        writer.println(message);
    }

    public boolean checkHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

//    public String getText() {
//        return text;
//    }


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}



