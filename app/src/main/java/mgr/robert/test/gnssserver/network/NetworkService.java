package mgr.robert.test.gnssserver.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import mgr.robert.test.gnssserver.network.handlers.Handler;
import mgr.robert.test.gnssserver.network.handlers.HandlerFactory;

public class NetworkService implements Runnable {
    private final HandlerFactory handlerFactory;
    private final ServerSocket serverSocket;

    NetworkService(int port, HandlerFactory handlerFactory)
            throws IOException {
        this.handlerFactory = handlerFactory;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Handler handler =
                        handlerFactory.getHandler(serverSocket.accept());
                handler.handle();
            }catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void close() throws Exception {
        serverSocket.close();
    }
}
