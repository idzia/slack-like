package com.codecool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 9000;
    private static final String HOSTNAME = "localhost";
    private static ClientSender sender;
    private static String clientNick;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOSTNAME, PORT)) {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String welcomeStr = reader.readLine();
            System.out.println(welcomeStr);
            System.out.println("Enter your NICK: ");
            Scanner scanner = new Scanner(System.in);
            clientNick = scanner.nextLine();
            System.out.println("You can start chat");

            sender  = new ClientSender(socket, clientNick);
            Thread s = new Thread(sender);
            s.start();

            String text;
            do {
                text = reader.readLine();
                System.out.println(text);

            } while (!text.equals("#exit"));


        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
