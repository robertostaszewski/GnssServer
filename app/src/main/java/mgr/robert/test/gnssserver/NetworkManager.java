package mgr.robert.test.gnssserver;

import android.util.SparseArray;

import java.io.IOException;

public class NetworkManager {
    private final SubscriberService subscriberService;
    private final int bufferSize;
    private final SparseArray<NetworkService> createdServices = new SparseArray<>();

    public NetworkManager(SubscriberService subscriberService, int bufferSize) {
        this.subscriberService = subscriberService;
        this.bufferSize = bufferSize;
    }

    public NetworkService getProducerNetwork(int port) throws IOException {
        if (createdServices.get(port) == null) {
            ProducerHandlerFactory factory =
                    new ProducerHandlerFactory(subscriberService, bufferSize);
            NetworkService networkService = new NetworkService(port, factory);
            createdServices.put(port, networkService);
            return networkService;
        } else {
            return createdServices.get(port);
        }
    }

    public NetworkService getConsumerNetwork(int port) throws IOException {
        if (createdServices.get(port) == null) {
            SubscribedConsumerHandlerFactory factory =
                    new SubscribedConsumerHandlerFactory(subscriberService, bufferSize);
            NetworkService networkService = new NetworkService(port, factory);
            createdServices.put(port, networkService);
            return networkService;
        } else {
            return createdServices.get(port);
        }
    }

    public void close() {
        for(int i = 0; i < createdServices.size(); i++) {
            try {
                createdServices.valueAt(i).close();
                createdServices.removeAt(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
