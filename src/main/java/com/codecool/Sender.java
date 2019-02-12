//package com.codecool;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.util.List;
//
//public class Sender implements Runnable {
//    private String text;
//    private List<ServerThread> serverList;
//
//    public void run() {
//
//
////        try {
//            while (true) {
//
////                synchronized (this) {
////                    serverList = Server.serverList;
////                }
////                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
////                if((text = reader.readLine())!=null){
//                serverList = Server.serverList;
//
//                if (serverList != null) {
//                    for (ServerThread server : serverList) {
//                        if (server.checkHasMessage()) {
//                            String message = server.getText();
//                            for (ServerThread newServer : serverList) {
//                                newServer.sendMessage(message);
//                            }
//
//                            server.setHasMessage(false);
//                        }
//                    }
//
//                }
//            }
//    }
////        } catch (IOException e) {
////                e.printStackTrace();
////                return;
////        }
//}
//
