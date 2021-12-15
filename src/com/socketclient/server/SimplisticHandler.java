package com.socketclient.server;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import com.socketclient.utils.ACK;
import com.socketclient.utils.Message;
import com.socketclient.utils.StaticValues;

import java.io.*;

public class SimplisticHandler extends UntypedActor {

    final LoggingAdapter log = Logging
            .getLogger(getContext().system(), getSelf());

    private ActorRef sender;

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Tcp.Received) {
            final ByteString data = ((Tcp.Received) msg).data();
            if(sender == null)
                sender = getSender();
            byte[] dataByte = data.toArray();
            Message message = null;
            try {
                message = (Message) deserialize(dataByte);
            }catch (EOFException e){
                log.error(e.getMessage());
                StaticValues.dumped.add(data);
            }
            assert message != null;
            log.info("Message " + message.getMessage());

            Thread.sleep(1000);

            ACK ack = ACK.newInstance();
            ack.setMessage("OK!");
            sender.tell(TcpMessage.resumeWriting(), getSelf());
            sender.tell(TcpMessage.write(ByteString.fromArray(serialize(ack))), getSelf());
            sender.tell(TcpMessage.confirmedClose(), getSelf());

            this.performDumped();
        } else if (msg instanceof Tcp.ConnectionClosed) {
            getContext().stop(getSelf());
        }
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }


    public void performDumped(){
        if(StaticValues.dumped.size() == 0)
            return;
        log.info("Processing dumped chunk of size: "+StaticValues.dumped.size());
        for(ByteString data : StaticValues.dumped){
            byte[] dataByte = data.toArray();
            ACK ack = null;
            try {
                ack = (ACK) deserialize(dataByte);
            }catch (EOFException e){
                log.error(e.getMessage());
                StaticValues.dumped.add(data);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            assert ack != null;
            log.info(ack.getMessage());
            sender.tell(TcpMessage.resumeReading(), getSelf());
            sender.tell(TcpMessage.write(ByteString.fromArray(dataByte)), getSelf());
            sender.tell(TcpMessage.confirmedClose(), getSelf());
        }
    }
}