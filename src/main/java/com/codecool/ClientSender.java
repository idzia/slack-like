package com.codecool;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientSender implements Runnable {

    private ObjectOutputStream preparedMessage;
    private OutputStream output;
    private BufferedReader reader;
    private Socket socket;
    private Message message;
    private String activeChannel = "*welcome*";
//    private String activeChannel = "*welcome*";
    private String clientNick;
    private String text;

    public ClientSender(Socket socket, String clientNick) {
        this.socket = socket;
        this.clientNick = clientNick;
    }

    public void run() {
        try {
            output = socket.getOutputStream();
            preparedMessage = new ObjectOutputStream(output);

            message = new Message(" joined to SLACK", clientNick, activeChannel);
            preparedMessage.writeObject(message);
            activeChannel = null;

            while (true) {

                reader = new BufferedReader(new InputStreamReader(System.in));

                if((text = reader.readLine())!=null){

                    message = new Message(text, clientNick, activeChannel);

                    if (message.isJoinMessage()) {

                        activeChannel = message.getChannelFromContent();

                    } else if (message.isLeaveMessage()) {

                        activeChannel = null;
                    }
                    preparedMessage.writeObject(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
