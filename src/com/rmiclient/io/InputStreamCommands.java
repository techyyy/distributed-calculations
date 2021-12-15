package com.rmiclient.io;

import java.io.IOException;
import java.rmi.Remote;

public interface InputStreamCommands extends Remote {

    byte[] readBytes(int len) throws IOException;

    int read() throws IOException;

    void close() throws IOException;

}