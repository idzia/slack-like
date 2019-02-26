package com.codecool.serverPart;

import com.codecool.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable {

    private int PORT;
    private Socket socket;
    private List<ServerThread> serverClientList = new ArrayList<>();
    private List<String> usersNicks = new ArrayList<>();
    private Map<String, List<ServerThread>> channelsMap = new HashMap<>();

    public Server(int port) {
        this.PORT = port;
    }

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Waiting for a client on port: " + PORT);
            AcceptNewClient acceptClient = new AcceptNewClient(serverSocket);
            acceptClient.start();

            while (true) {

                if (acceptClient.isNewClient()){
                    handleNewClient(acceptClient);
                }

                if (serverClientList != null) {
                    handleSendingMessages();
                }
            }
        } catch (IOException e) {
            System.out.println("During creating the socket occurred i/o error");
        }
    }

    private void handleSendingMessages() throws IOException {
        for (ServerThread serverClient : serverClientList) {

            if (serverClient.hasMessage()) {
                Message message = serverClient.getMessage();

                if (message.isJoinMessage()) {
                    handleJoinMessage(message, serverClient);

                } else if (message.isLeaveMessage()) {
                    handleLeaveMessage(message, serverClient);

                } else if (message.isWelcomeMessage()) {
                    handleWelcomeMessage(message, serverClient);

                } else {
                    handleSendToChannel(message);

                }
                serverClient.setHasMessage(false);
                serverClient.setMessage(null);
            }
        }
    }

    private void handleSendToChannel(Message message) throws IOException {
        System.out.println("Message");
        String channel = message.getChannel();
        if (channel != null){
            for (ServerThread serverThread : channelsMap.get(channel)) {
                serverThread.sendMessage(message);
            }
        }
    }

    private void handleWelcomeMessage(Message message, ServerThread serverClient) throws IOException {
        System.out.println("WELCOME command");
        String author = message.getAuthor();
        usersNicks.add(author);
        Message messageInit = new Message(author + " join to SLACK", "server", "init");
        for (ServerThread serverThread : serverClientList) {
            serverThread.sendMessage(messageInit);
        }
        String content = contentForActiveUserInfo();
        Message activeUserInfo = new Message(content, "server", "init" );
        serverClient.sendMessage(activeUserInfo);
    }

    private void handleLeaveMessage(Message message, ServerThread serverClient) throws IOException {
        System.out.println("LEAVE command");
        String channel = message.getChannelFromContent();
        if (channelsMap.containsKey(channel)) {
            channelsMap.get(channel).remove(serverClient);
        }
        String content = contentForActiveUserInfo();
        Message activeUserInfo = new Message(content, "server", "init" );
        serverClient.sendMessage(activeUserInfo);
    }

    private void handleJoinMessage(Message message, ServerThread serverClient) {
        System.out.println("JOIN command");
        String channel = message.getChannelFromContent();
        if (!channelsMap.containsKey(channel)) {

            List<ServerThread> serverThreadList = new ArrayList<>();
            channelsMap.put(channel, serverThreadList);
        }
        channelsMap.get(channel).add(serverClient);
    }

    private void handleNewClient(AcceptNewClient acceptClient) {
        socket = acceptClient.getSocket();
        ServerThread serverClientSockets = new ServerThread(socket);

        serverClientList.add(serverClientSockets);
        serverClientSockets.start();

        System.out.println("Active users: " + serverClientList.size());
        acceptClient.setNewClient(false);
    }

    private String contentForActiveUserInfo() {

        StringBuilder stringBuilder = new StringBuilder();

        for (String key : channelsMap.keySet()) {
            stringBuilder.append("Active channels: \n");
            stringBuilder.append(key);
            stringBuilder.append(", ");
        }
        stringBuilder.append("\n Active users: \n");

         for (String nick : usersNicks) {
             stringBuilder.append(nick);
             stringBuilder.append(", ");
         }

        return stringBuilder.toString();
    }

}
