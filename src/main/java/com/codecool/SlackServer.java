package com.codecool;

import com.codecool.serverPart.Server;

public class SlackServer {
    private static final int PORT = 9000;

    public static void main(String[] args) {
        Server slackServer = new Server(PORT);
        slackServer.run();
    }
}
