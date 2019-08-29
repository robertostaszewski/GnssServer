package mgr.robert.test.gnssserver.network;

import android.util.SparseArray;

import java.io.IOException;

import mgr.robert.test.gnssserver.network.handlers.ProducerHandlerFactory;
import mgr.robert.test.gnssserver.network.handlers.SubscribedConsumerHandlerFactory;

public class NetworkManager {
    private static final int BUFFER_SIZE = 1024;
    private final SubscriberService subscriberService;
    private final SparseArray<NetworkService> createdServices = new SparseArray<>();

    public NetworkManager(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public NetworkService getProducerNetwork(int port) throws IOException {
        if (isBusy(port)) {
            closeNetworkAtPort(port);
        }
        ProducerHandlerFactory factory =
                new ProducerHandlerFactory(subscriberService, BUFFER_SIZE);
        NetworkService networkService = new NetworkService(port, factory);
        createdServices.put(port, networkService);
        return networkService;
    }

    public NetworkService getConsumerNetwork(int port) throws IOException {
        if (isBusy(port)) {
            closeNetworkAtPort(port);
        }
        SubscribedConsumerHandlerFactory factory =
                new SubscribedConsumerHandlerFactory(subscriberService, BUFFER_SIZE);
        NetworkService networkService = new NetworkService(port, factory);
        createdServices.put(port, networkService);
        return networkService;
    }

    public void closeAll() {
        int size = createdServices.size();
        for (int i = 0; i < size; i++) {
            closeNetworkAtPort(createdServices.keyAt(i));
        }
    }

    private boolean isBusy(int port) {
        return createdServices.get(port) != null;
    }

    private void closeNetworkAtPort(int port) {
        try {
            createdServices.get(port).close();
            createdServices.remove(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
