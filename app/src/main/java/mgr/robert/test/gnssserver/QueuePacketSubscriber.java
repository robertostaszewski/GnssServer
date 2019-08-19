package mgr.robert.test.gnssserver;

import java.util.concurrent.BlockingQueue;

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
