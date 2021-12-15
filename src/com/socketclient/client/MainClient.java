package com.socketclient.client;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {

            for(int i = 0;i < 200; i++){
                ClientThread cl = new ClientThread();
                Thread th = new Thread(cl);
                th.start();

            }

        Client client = new Client();
        client.connectToServer();
        Client client2 = new Client();
        client2.connectToServer();
    }
}
