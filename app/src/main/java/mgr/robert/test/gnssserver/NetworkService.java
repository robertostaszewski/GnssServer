package mgr.robert.test.gnssserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class NetworkService implements Runnable {
    private final HandlerFactory handlerFactory;
    private final ServerSocket serverSocket;

    NetworkService(int port, HandlerFactory handlerFactory) throws IOException {
        this.handlerFactory = handlerFactory;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handlerFactory.getHandler(serverSocket.accept()).handle();
            }catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws Exception {
        serverSocket.close();
    }
}
