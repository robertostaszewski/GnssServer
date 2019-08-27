package mgr.robert.test.gnssserver.network.handlers;

import java.net.Socket;

public interface HandlerFactory {
    Handler getHandler(Socket socket);
}
