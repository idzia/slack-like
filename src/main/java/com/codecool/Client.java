package com.codecool;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

    private int port;
    private String hostname;
    private ClientSender sender;
    private String clientNick;
    private Message message;
    private ObjectInputStream incomingMessage;
    private InputStream input;

    public Client(int port, String hostname, String clientNick) {
        this.port = port;
        this.hostname = hostname;
        this.clientNick = clientNick;
    }

    public void run() {

        try (Socket socket = new Socket(hostname, port)) {

            System.out.println("You can use command: /join #name, /leave #name");

            sender  = new ClientSender(socket, clientNick);
            Thread s = new Thread(sender);
            s.start();

            input = socket.getInputStream();
            incomingMessage = new ObjectInputStream(input);

            while (true) {
                if(incomingMessage != null) {
                    message = (Message) incomingMessage.readObject();
                    System.out.println(message.toString());
                }
            }

        } catch (UnknownHostException ex) {
            System.out.println("Client can not found server" + hostname + " " + port);

        } catch (IOException ex) {
            System.out.println("During taking stream by client occurred IO error");

        } catch (ClassNotFoundException e) {
            System.out.println("Object Input Stream can not be convert to Message");
        }
    }

}
