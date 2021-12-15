package com.rmiclient.io;

import java.io.IOException;
import java.rmi.Remote;

public interface OutputStreamCommands extends Remote {
    void write(int b) throws IOException;

    void write(byte[] b, int off, int len) throws IOException;

    void close() throws IOException;
}
