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

            initializeWelcomeChannel();

            while (true) {

                if (acceptClient.isNewClient()){

                    socket = acceptClient.getSocket();
                    ServerThread serverClientSockets = new ServerThread(socket);

                    serverClientList.add(serverClientSockets);
                    serverClientSockets.start();



//                    String content = contentForActiveUserInfo();
//                    Message activeUserInfo = new Message(content, "server", "init" );
//                    serverClientSockets.sendMessage(activeUserInfo);

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

                            } else {

                                String channel = message.getChannel();
                                String author = message.getAuthor();
                                if (channel.equals("*welcome*")) {
                                    System.out.println("Author: " + author);
                                    usersNicks.add(author);


                                    System.out.println("'*welcome*' channel was found");


                                    channelsMap.get(channel).add(serverClient);
                                    for (ServerThread serverThread : channelsMap.get(channel)) {
                                        serverThread.sendMessage(message);
                                    }

                                    String content = contentForActiveUserInfo();
                                    Message activeUserInfo = new Message(content, "server", "init" );
                                    serverClient.sendMessage(activeUserInfo);

                                }
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

    public static void initializeWelcomeChannel() {
        List<ServerThread> initServerThreadList = new ArrayList<>();
        channelsMap.put("*welcome*", initServerThreadList);
    }

    private static String contentForActiveUserInfo() {

        StringBuilder stringBuilder = new StringBuilder();

        for (String key : channelsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append(", ");
        }
        stringBuilder.append("\n");

         for (String nick : usersNicks) {
             stringBuilder.append(nick);
             stringBuilder.append(", ");
         }

//        for (Map.Entry<String, List<ServerThread>> entry : channelsMap.entrySet()) {
//            stringBuilder.append(entry.getKey());
//            List<ServerThread> valueList = entry.getValue();
//            for (ServerThread serverThread : valueList) {
//
//            }
//        }
//
//        for (ServerThread serverThread : serverClientList) {
//            stringBuilder.append(serverThread.)
//        }


        return stringBuilder.toString();
    }

//
//    public static synchronized void addToList(ServerThread serverClientSockets){
//        serverClientList.add(serverClientSockets);
//    }
//
//    public static List<ServerThread> getServerClientList() {
//        return serverClientList;
//    }


}
