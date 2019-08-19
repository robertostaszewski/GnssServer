package mgr.robert.test.gnssserver;

import java.net.Socket;

public interface HandlerFactory {
    Handler getHandler(Socket socket);
}
