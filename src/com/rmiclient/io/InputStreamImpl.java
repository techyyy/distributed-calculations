package com.rmiclient.io;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.UnicastRemoteObject;

public class InputStreamImpl implements InputStreamCommands {

    private final InputStream in;
    private byte[] b;

    public InputStreamImpl(InputStream in) throws IOException {
        this.in = in;
        UnicastRemoteObject.exportObject(this, 8087);
    }

    public void close() throws IOException{
        in.close();
    }

    public int read() throws IOException {
        return in.read();
    }

    public byte[] readBytes(int len) throws IOException {
        if (b == null || b.length != len)
            b = new byte[len];

        int len2 = in.read(b);
        if (len2 < 0)
            return null;

        if (len2 != len) {
            byte[] b2 = new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        } else
            return b;
    }
}
