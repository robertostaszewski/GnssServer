package mgr.robert.test.gnssserver;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class SubscribedConsumerHandlerFactory implements HandlerFactory {

    private final SubscriberService subscriberService;
    private final int bufferSize;

    public SubscribedConsumerHandlerFactory(SubscriberService subscriberService, int bufferSize) {
        this.subscriberService = subscriberService;
        this.bufferSize = bufferSize;
    }

    @Override
    public Handler getHandler(Socket socket) {
        LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
        PacketSubscriber packetSubscriber = new QueuePacketSubscriber(packets);
        return new AutoRegisterOnHandle(
                new ConsumerHandler(socket, packets, bufferSize), packetSubscriber, subscriberService);
    }
}
