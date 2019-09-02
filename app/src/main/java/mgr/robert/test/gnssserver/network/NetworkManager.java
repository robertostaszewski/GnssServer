package mgr.robert.test.gnssserver.network;

import android.util.Log;
import android.util.SparseArray;

import java.io.IOException;

import mgr.robert.test.gnssserver.network.handlers.HandlerFactory;
import mgr.robert.test.gnssserver.network.handlers.ProducerHandlerFactory;
import mgr.robert.test.gnssserver.network.handlers.SubscribedConsumerHandlerFactory;

public class NetworkManager {
    private final SubscriberService subscriberService;
    private final SparseArray<NetworkService> createdServices = new SparseArray<>();

    public NetworkManager(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public NetworkService getProducerNetwork(int port) throws IOException {
        return getNetworkService(port, new ProducerHandlerFactory(subscriberService));
    }

    public NetworkService getConsumerNetwork(int port) throws IOException {
        return getNetworkService(port, new SubscribedConsumerHandlerFactory(subscriberService));
    }

    private NetworkService getNetworkService(int port, HandlerFactory handlerFactory) throws IOException {
        if (isBusy(port)) {
            closeNetworkAtPort(port);
        }
        NetworkService networkService = new NetworkService(port, handlerFactory);
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
            Log.e("NM", "exception in closeNetworkAtPort: " + port, e);
        }
    }
}
