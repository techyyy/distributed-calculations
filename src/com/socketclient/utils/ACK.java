package com.socketclient.utils;

import java.io.Serializable;
import java.util.Objects;

public class ACK implements Serializable{

    private static final long serialVersionUID = 4L;

    private ACK(){
    }

    public static ACK newInstance(){
        return new ACK();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ACK ack = (ACK) o;
        return Objects.equals(message, ack.message);
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() + 64 : 0;
    }

    @Override
    public String toString() {
        return "ACK{" +
                "message='" + message + '\'' +
                '}';
    }
}
