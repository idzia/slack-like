package com.codecool;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientSender implements Runnable {
    private String text;
    private BufferedReader reader;
    private Socket socket;
    public PrintWriter writer;
    private String clientNick;

    public ClientSender(Socket socket, String clientNick) {
        this.socket = socket;
        this.clientNick = clientNick;
    }

    public void run() {
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            while (true) {
//                BufferedReader
                        reader = new BufferedReader(new InputStreamReader(System.in));
                if((text = reader.readLine())!=null){
                    writer.println(clientNick + ": " + text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
