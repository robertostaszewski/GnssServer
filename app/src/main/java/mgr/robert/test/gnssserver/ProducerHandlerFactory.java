package mgr.robert.test.gnssserver;

import java.net.Socket;

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
                new CopyOnSendPacketPublisher(subscriberService.getSubscribers()), bufferSize);
    }
}
