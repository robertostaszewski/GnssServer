package mgr.robert.test.gnssserver;

import java.util.List;

public interface SubscriberService {
    void addSubscriber(PacketSubscriber packetSubscriber);

    void removeSubscriber(PacketSubscriber packetSubscriber);

    List<PacketSubscriber> getSubscribers();

    void removeAll();
}
