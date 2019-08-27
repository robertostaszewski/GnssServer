package mgr.robert.test.gnssserver.network.publisher;

import java.util.List;

import mgr.robert.test.gnssserver.network.Packet;
import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;

public class CopyPacketOnSendPublisher implements PacketPublisher {
    private final List<PacketSubscriber> packetSubscribers;

    public CopyPacketOnSendPublisher(List<PacketSubscriber>
                                             packetSubscribers) {
        this.packetSubscribers = packetSubscribers;
    }

    @Override
    public void sendPacket(Packet packet) {
        for (PacketSubscriber packetSubscriber : packetSubscribers) {
            packetSubscriber.consume(Packet.from(packet));
        }
    }
}
