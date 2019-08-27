package mgr.robert.test.gnssserver.network.subscriber;

import mgr.robert.test.gnssserver.network.Packet;

public interface PacketSubscriber {

    void consume(Packet packet);
}
