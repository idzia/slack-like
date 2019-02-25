package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server{

    private static final int PORT = 9000;
    public static List<ServerThread> serverList = new ArrayList<>();


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
//                    addToList(serverClientSockets);
                    serverList.add(serverClientSockets);
                    serverClientSockets.start();
                    System.out.println(serverList.size());
                    acceptClient.setNewClient(false);
                }


                if (serverList != null) {
                    for (ServerThread server : serverList) {
                        if (server.checkHasMessage()) {
//                            String message = server.getText();
                            Message message = server.getMessage();
                            for (ServerThread newServer : serverList) {
//                                newServer.sendMessage(message);
                                newServer.sendMessage(message);
                            }

                            server.setHasMessage(false);
                            server.setMessage(null);
                        }
                    }

                }

            }

        } catch (IOException e) {
            System.out.println("During creating the socket occurred i/o error");
        }
    }

    public static synchronized void addToList(ServerThread serverClientSockets){
        serverList.add(serverClientSockets);
    }

}
