package com.socketclient.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import akka.io.Tcp;
import com.socketclient.utils.StaticValues;

public class MainServer {

    public static void main(String... args){
        ActorSystem system = ActorSystem.create("server-tcp");
        final ActorRef deadLetterActor = system.actorOf(Props.create(DeadLetterActor.class));
        system.eventStream().subscribe(deadLetterActor, DeadLetter.class);
        ActorRef server = system.actorOf(Props.create(Server.class, Tcp.get(system).getManager()), "server");
        while(!StaticValues.isConnected){
            System.out.println("Waiting");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
