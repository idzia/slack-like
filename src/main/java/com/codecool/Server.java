package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.*;

public class Server {

    private static final int PORT = 9000;
    private static List<ServerThread> serverClientList = new ArrayList<>();
    private static List<String> usersNicks = new ArrayList<>();
    private static Map<String, List<ServerThread>> channelsMap = new HashMap<>();


    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Waiting for a client on port: " + PORT);
            Socket socket = null;
            AcceptNewClient acceptClient = new AcceptNewClient(serverSocket);
            acceptClient.start();

            while (true) {

                if (acceptClient.isNewClient()){

                    socket = acceptClient.getSocket();
                    ServerThread serverClientSockets = new ServerThread(socket);

                    serverClientList.add(serverClientSockets);
                    serverClientSockets.start();

                    System.out.println("Active users: " + serverClientList.size());
                    acceptClient.setNewClient(false);
                }

                if (serverClientList != null) {

                    for (ServerThread serverClient : serverClientList) {

                        if (serverClient.hasMessage()) {
                            Message message = serverClient.getMessage();

                            if (message.isJoinMessage()) {

                                System.out.println("JOIN command");
                                String channel = message.getChannelFromContent();
                                if (!channelsMap.containsKey(channel)) {

                                    List<ServerThread> serverThreadList = new ArrayList<>();
                                    channelsMap.put(channel, serverThreadList);
                                }
                                    channelsMap.get(channel).add(serverClient);

                            } else if (message.isLeaveMessage()) {

                                System.out.println("LEAVE command");
                                String channel = message.getChannelFromContent();
                                if (channelsMap.containsKey(channel)) {
                                    channelsMap.get(channel).remove(serverClient);
                                }
                                String content = contentForActiveUserInfo();
                                Message activeUserInfo = new Message(content, "server", "init" );
                                serverClient.sendMessage(activeUserInfo);


                            } else if (message.isWelcomeMessage()) {

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


                            } else {

                                System.out.println("Message");
                                String channel = message.getChannel();
                                if (channel != null){
                                    for (ServerThread serverThread : channelsMap.get(channel)) {
                                        serverThread.sendMessage(message);
                                    }
                                }
                            }
                            serverClient.setHasMessage(false);
                            serverClient.setMessage(null);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("During creating the socket occurred i/o error");
        }
    }

    private static String contentForActiveUserInfo() {

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
