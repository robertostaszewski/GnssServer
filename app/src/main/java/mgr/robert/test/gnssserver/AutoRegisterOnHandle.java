package mgr.robert.test.gnssserver;

public class AutoRegisterOnHandle implements Handler {
    private final Handler handler;
    private final PacketSubscriber packetSubscriber;
    private final SubscriberService subscriberService;

    public AutoRegisterOnHandle(Handler handler,
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
