package mgr.robert.test.gnssserver;

import java.util.List;

public class CopyOnSendPacketPublisher implements PacketPublisher {
    private final List<PacketSubscriber> packetSubscribers;

    public CopyOnSendPacketPublisher(List<PacketSubscriber> packetSubscribers) {
        this.packetSubscribers = packetSubscribers;
    }

    @Override
    public void sendPacket(Packet packet) {
        for (PacketSubscriber packetSubscriber : packetSubscribers) {
            packetSubscriber.consume(Packet.from(packet));
        }
    }
}
