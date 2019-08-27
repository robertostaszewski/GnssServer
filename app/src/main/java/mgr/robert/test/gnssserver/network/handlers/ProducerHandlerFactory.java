package mgr.robert.test.gnssserver.network.handlers;

import java.net.Socket;

import mgr.robert.test.gnssserver.network.publisher.CopyPacketOnSendPublisher;
import mgr.robert.test.gnssserver.network.SubscriberService;

public class ProducerHandlerFactory implements HandlerFactory {
    private final SubscriberService subscriberService;
    private final int bufferSize;

    public ProducerHandlerFactory(SubscriberService subscriberService, int bufferSize) {
        this.subscriberService = subscriberService;
        this.bufferSize = bufferSize;
    }

    @Override
    public Handler getHandler(Socket socket) {
        return new ProducerHandler(socket,
                new CopyPacketOnSendPublisher(subscriberService.getSubscribers()), bufferSize);
    }
}
