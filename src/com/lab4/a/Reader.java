package com.lab4.a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements Runnable {

    private ReaderInstruction instruction;
    private String searchingKey;
    private final String fileName;
    private final ReadWriteLock lock;
    private volatile String result = null;

    public Reader(String fileName, ReadWriteLock lock) {
        this.fileName = fileName;
        this.lock = lock;
    }

    public String performSearch(ReaderInstruction instruction, String searchingKey) throws InterruptedException {
        result = null;
        this.instruction = instruction;
        this.searchingKey = searchingKey;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return result;
    }

    public void run() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            lock.readLock();
            String foundString;
            while ((foundString = reader.readLine()) != null) {
                int separator = foundString.indexOf(':');
                String name = foundString.substring(0, separator);
                String number = foundString.substring(separator + 1);
                if (isThereRecord(name, number)) {
                    lock.readUnlock();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readUnlock();
        }
    }

    private boolean isThereRecord(String name, String number) {
        switch (instruction){
            case FIND_NAME_BY_NUMBER: {
                if(number.equals(searchingKey)) {
                    result = name;
                    return true;
                }
                break;
            }
            case FIND_NUMBER_BY_NAME: {
                if(name.equals(searchingKey)) {
                    result = number;
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
