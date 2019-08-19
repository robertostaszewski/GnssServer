package mgr.robert.test.gnssserver;

public interface PacketSubscriber {
    void consume(Packet packet);
}
