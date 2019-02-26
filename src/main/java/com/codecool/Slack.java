package com.codecool;

import java.util.Scanner;

public class Slack {
    private static final int PORT = 9000;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your NICK: ");
        String clientNick = scanner.nextLine();

        Client client = new Client(PORT, HOSTNAME, clientNick);
        client.run();
    }

}
