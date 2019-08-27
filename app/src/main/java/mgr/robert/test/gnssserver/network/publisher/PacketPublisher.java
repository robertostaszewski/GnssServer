package mgr.robert.test.gnssserver.network.publisher;

import mgr.robert.test.gnssserver.network.Packet;

public interface PacketPublisher {

    void sendPacket(Packet packet);
}
