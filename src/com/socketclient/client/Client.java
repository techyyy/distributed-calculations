package com.socketclient.client;

import com.socketclient.utils.ACK;
import com.socketclient.utils.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private ObjectInputStream in;

    public void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 8086);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        Message message = Message.newInstance();
        message.setMessage("Connected");
        out.writeObject(message);
        out.flush();

        new Thread(() -> {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                ACK response = null;
                try {
                    response = (ACK) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                assert response != null;
                System.out.println("Received ACK: " + response.getMessage());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (NullPointerException e) {
                System.err.println("Object is null");
            }
        }).start();

        out.close();
        socket.close();
    }

}
