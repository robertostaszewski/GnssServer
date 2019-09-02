package mgr.robert.test.gnssserver.network.subscriber;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueuePacketSubscriber that = (QueuePacketSubscriber) o;
        return Objects.equals(packets, that.packets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packets);
    }
}
