package com.codecool;

import com.codecool.clientPart.Client;

import java.util.Scanner;

public class Slack {
    private static final int PORT = 9000;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {

        String clientNick = getUserNick();

        Client client = new Client(PORT, HOSTNAME, clientNick);
        client.run();
    }

    private static String getUserNick() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your NICK: ");

        return scanner.nextLine();
    }

}
