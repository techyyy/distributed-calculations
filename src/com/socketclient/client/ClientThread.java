package com.socketclient.client;

import java.io.IOException;

public class ClientThread implements Runnable{

    @Override
    public void run() {
        Client client = new Client();
        try {
            client.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
