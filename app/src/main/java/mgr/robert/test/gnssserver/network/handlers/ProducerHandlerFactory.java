package mgr.robert.test.gnssserver.network.handlers;

import java.net.Socket;
import java.util.List;

import mgr.robert.test.gnssserver.network.publisher.CopyPacketOnSendPublisher;
import mgr.robert.test.gnssserver.network.SubscriberService;
import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;

public class ProducerHandlerFactory implements HandlerFactory {
    private final SubscriberService subscriberService;

    public ProducerHandlerFactory(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public Handler getHandler(Socket socket) {
        List<PacketSubscriber> subscribers = subscriberService.getSubscribers();
        CopyPacketOnSendPublisher packetPublisher = new CopyPacketOnSendPublisher(subscribers);
        return new ProducerHandler(socket, packetPublisher);
    }
}
