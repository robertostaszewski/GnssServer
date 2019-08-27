package mgr.robert.test.gnssserver.network.subscriber;

import java.util.concurrent.BlockingQueue;

import mgr.robert.test.gnssserver.network.Packet;

public class QueuePacketSubscriber implements PacketSubscriber {

    private final BlockingQueue<Packet> packets;

    public QueuePacketSubscriber(BlockingQueue<Packet> packets) {
        this.packets = packets;
    }

    @Override
    public void consume(Packet packet) {
        packets.add(packet);
    }
}
