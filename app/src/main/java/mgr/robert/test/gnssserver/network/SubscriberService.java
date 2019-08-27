package mgr.robert.test.gnssserver.network;

import java.util.List;

import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;

public interface SubscriberService {
    void addSubscriber(PacketSubscriber packetSubscriber);

    void removeSubscriber(PacketSubscriber packetSubscriber);

    List<PacketSubscriber> getSubscribers();
}
