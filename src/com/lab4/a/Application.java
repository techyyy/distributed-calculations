package com.lab4.a;

import java.security.SecureRandom;

public class Application {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String FILE_NAME = "db.txt";
    private static final String NAME_TO_SEARCH = "Name75";
    private static final String NUMBER_TO_SEARCH = "115557";

    public static void main(String... args) {

        ReadWriteLock lock = new ReadWriteLock();
        Reader reader = new Reader(FILE_NAME, lock);
        Writer writer = new Writer(FILE_NAME, lock);

        try {
            System.out.println("Added: "
                    + writer.changeFile(WriterInstruction.ADD, "Name"
                    + RANDOM.nextInt(100), "11"
                    + (RANDOM.nextInt(8999) + 1000)));
            System.out.println("Name with number "
                    + NUMBER_TO_SEARCH
                    + ": "
                    + reader.performSearch(ReaderInstruction.FIND_NAME_BY_NUMBER, NUMBER_TO_SEARCH));
            System.out.println("Number of "
                    + NAME_TO_SEARCH
                    + ": "
                    + reader.performSearch(ReaderInstruction.FIND_NUMBER_BY_NAME, NAME_TO_SEARCH));
        } catch (InterruptedException e) {
            System.err.println("Interrupted");;
        }
    }
}
