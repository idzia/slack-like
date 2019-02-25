package com.codecool;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 9000;
    private static final String HOSTNAME = "localhost";
    private static ClientSender sender;
    private static String clientNick;
    private static Message message;
    private static ObjectInputStream incomingMessage;
    private static InputStream input;
//    private Socket socket;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOSTNAME, PORT)) {
            //InputStream
            input = socket.getInputStream();

            welcome();

            sender  = new ClientSender(socket, clientNick);
            Thread s = new Thread(sender);
            s.start();

            incomingMessage = new ObjectInputStream(input);

//            String text;
            do {
//                text = reader.readLine();
//                System.out.println(text);
                if(incomingMessage != null) {
                    message = (Message) incomingMessage.readObject();
                    System.out.println(message.toString());
                }

//            } while (!text.equals("#exit"));
            }while (true);


        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("I can not found appropriate class");
        }
    }

    public static void welcome() {

        System.out.println("Enter your NICK: ");
        Scanner scanner = new Scanner(System.in);
        clientNick = scanner.nextLine();
        System.out.println("You can start chat");

    }

}
