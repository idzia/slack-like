//package com.codecool;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//public class Receiver implements Runnable {
//    private BufferedReader reader;
//    private InputStreamReader isr;
//    private List<ServerThread> serverList;
//
//    public void run() {
//
//
//
//            while (true) {
//                synchronized (this) {
//                    serverList = Server.serverList;
//                }
//
////                try {
//                if (serverList != null) {
//
//
//                    for (ServerThread server : serverList) {
//                        if (server.checkHasMessage()) {
//                            System.out.println(server.getText());
//                        }
//
//
////                        if (server.getInput() != null) {
////                            reader = new BufferedReader(new InputStreamReader(server.getInput()));
//////                            text = reader.readLine();
////                            System.out.println("server.getInput()");
//////                            System.out.println("server.getInput()" + text);
////                            if ((text = reader.readLine()) != null) {
////                                System.out.println(text);
//////                        if (server.getMessage()!= null) {
//////                            System.out.println(server.getMessage());
////                            }
////                        }
////                        reader = new BufferedReader(new InputStreamReader(server.getInput()));
//
//                    }
//                }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    return;
////                }
//            }
//    }
//}
