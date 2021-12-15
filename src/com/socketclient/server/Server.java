package com.socketclient.server;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import com.socketclient.utils.StaticValues;

import java.net.InetSocketAddress;

public class Server extends UntypedActor {

    private final ActorRef manager;

    public Server(ActorRef manager) {
        this.manager = manager;
    }

    @Override
    public void preStart() {
        manager.tell(TcpMessage.bind(getSelf(),
                new InetSocketAddress(StaticValues.server , StaticValues.port), 100), getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        if (msg instanceof Tcp.Bound) {
            manager.tell(msg, getSelf());

        } else if (msg instanceof Tcp.CommandFailed) {
            getContext().stop(getSelf());

        } else if (msg instanceof Tcp.Connected) {
            final Tcp.Connected conn = (Tcp.Connected) msg;
            manager.tell(conn, getSelf());
            ActorRef handler = getContext().actorOf(
                    Props.create(SimplisticHandler.class));
            getSender().tell(TcpMessage.register(handler, true, true), getSelf());
            StaticValues.isConnected = true;
        } else {
            unhandled(msg);
        }
    }

}