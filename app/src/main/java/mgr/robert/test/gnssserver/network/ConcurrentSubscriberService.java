package mgr.robert.test.gnssserver.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;

public class ConcurrentSubscriberService implements SubscriberService {
    private final List<PacketSubscriber> packetSubscribers = new CopyOnWriteArrayList<>();

    @Override
    public void addSubscriber(PacketSubscriber packetSubscriber) {
        if (!packetSubscribers.contains(packetSubscriber)) {
            packetSubscribers.add(packetSubscriber);
        }
    }

    @Override
    public void removeSubscriber(PacketSubscriber packetSubscriber) {
        packetSubscribers.remove(packetSubscriber);
    }

    @Override
    public List<PacketSubscriber> getSubscribers() {
        return packetSubscribers;
    }
}
