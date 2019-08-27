package mgr.robert.test.gnssserver.network.handlers;

import mgr.robert.test.gnssserver.network.subscriber.PacketSubscriber;
import mgr.robert.test.gnssserver.network.SubscriberService;

public class AutoRegisterOnHandle implements Handler {
    private final Handler handler;
    private final PacketSubscriber packetSubscriber;
    private final SubscriberService subscriberService;

    AutoRegisterOnHandle(Handler handler,
                         PacketSubscriber packetSubscriber,
                         SubscriberService subscriberService) {
        this.handler = handler;
        this.packetSubscriber = packetSubscriber;
        this.subscriberService = subscriberService;
    }

    @Override
    public void handle() {
        subscriberService.addSubscriber(packetSubscriber);
        handler.handle();
        subscriberService.removeSubscriber(packetSubscriber);
    }
}
