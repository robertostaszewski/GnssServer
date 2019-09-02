package mgr.robert.test.gnssserver.network.handlers;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import mgr.robert.test.gnssserver.network.Packet;
import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;
import mgr.robert.test.gnssserver.network.subscriber.QueuePacketSubscriber;
import mgr.robert.test.gnssserver.network.SubscriberService;

public class SubscribedConsumerHandlerFactory implements HandlerFactory {

    private final SubscriberService subscriberService;

    public SubscribedConsumerHandlerFactory(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public Handler getHandler(Socket socket) {
        LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
        PacketSubscriber packetSubscriber = new QueuePacketSubscriber(packets);
        ConsumerHandler consumerHandler = new ConsumerHandler(socket, packets);
        return new AutoRegisterOnHandle(consumerHandler, packetSubscriber, subscriberService);
    }
}
