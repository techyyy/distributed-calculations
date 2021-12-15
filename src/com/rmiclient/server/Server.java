package com.rmiclient.server;

import com.rmiclient.RemoteObjectImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Server {

    public static void main(String[] args) {
        ResourceBundle properties = PropertyResourceBundle.getBundle("Simple");
        int port = Registry.REGISTRY_PORT;
        try {
            port = Integer.parseInt(properties.getString("server.port"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            try {
                Boolean.valueOf(properties.getString("useSecurityManager"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Registry registry = LocateRegistry.createRegistry(port);

            RemoteObjectImpl obj = new RemoteObjectImpl();
            /* Bind this object instance to the name "com.rmiclient.server.SimpleServer" */
            // Naming.rebind("com.rmiclient.server.SimpleServer", obj);
            registry.rebind("com.rmiclient.server.SimpleServer", obj);

            System.out.println("com.rmiclient.server.SimpleServer started on port " + port);
        } catch (Exception e) {
            System.out.println("com.rmiclient.server.SimpleServer err: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 
