package com.rmiclient.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class RMIOutputStream extends OutputStream implements Serializable {

    private static final long serialVersionUID = 1L;

    private final OutputStreamCommands out;

    public RMIOutputStream(OutputStreamImpl out) {
        this.out = out;
    }

    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] b, int off, int len) throws
            IOException {
        out.write(b, off, len);
    }

    public void close() throws IOException {
        out.close();
    }
}
