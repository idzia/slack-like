package com.codecool.serverPart;

import com.codecool.Message;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private ObjectInputStream incomingMessage;
    private ObjectOutputStream preparedMessage;
    private Message message;
    private boolean hasMessage = false;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        try {

            output = socket.getOutputStream();
            preparedMessage = new ObjectOutputStream(output);

            input = socket.getInputStream();
            incomingMessage = new ObjectInputStream(input);

            while (true) {

                if (incomingMessage != null) {
                    message = (Message) incomingMessage.readObject();
                    hasMessage = true;
                }
            }

        } catch (IOException e) {
            System.out.println("During taking stream occurred IO error");
        } catch (ClassNotFoundException e) {
            System.out.println("Object Input Stream can not be convert to Message");
        }
    }

    public void sendMessage(Message message) throws IOException {
        preparedMessage.writeObject(message);
    }

    public boolean hasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}



