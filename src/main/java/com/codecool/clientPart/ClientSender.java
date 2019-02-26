package com.codecool.clientPart;

import com.codecool.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientSender implements Runnable {

    private ObjectOutputStream preparedMessage;
    private OutputStream output;
    private BufferedReader reader;
    private Socket socket;
    private Message message;
    private String activeChannel;
    private String clientNick;
    private String text;
    private final String WELCOME = "*welcome*";

    public ClientSender(Socket socket, String clientNick) {
        this.socket = socket;
        this.clientNick = clientNick;
    }

    public void run() {
        try {
            output = socket.getOutputStream();
            preparedMessage = new ObjectOutputStream(output);

            message = new Message(WELCOME, clientNick, activeChannel);
            preparedMessage.writeObject(message);

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
