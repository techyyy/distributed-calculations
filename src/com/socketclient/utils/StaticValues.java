package com.socketclient.utils;

import akka.util.ByteString;

import java.util.concurrent.LinkedBlockingQueue;

public class StaticValues {
    public static boolean isConnected = false;
    public static LinkedBlockingQueue<ByteString> dumped = new LinkedBlockingQueue<ByteString>();
    public static final int port = 8086;
    public static final String server = "localhost";
}
