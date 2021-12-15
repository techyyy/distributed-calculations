package com.socketclient.server;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;

public class DeadLetterActor extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if(message instanceof DeadLetter){
            DeadLetter letter = (DeadLetter) message;
            System.out.println("Message from dead letter " + letter.message());
        }
    }
}
