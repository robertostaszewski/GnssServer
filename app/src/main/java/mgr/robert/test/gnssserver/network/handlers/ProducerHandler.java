package mgr.robert.test.gnssserver.network.handlers;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import mgr.robert.test.gnssserver.network.Packet;
import mgr.robert.test.gnssserver.network.publisher.PacketPublisher;

public class ProducerHandler implements Handler {
    private static final String DELIMITER = "\\r\\n\\r\\n";
    private static final String SOURCE = "SOURCE";
    private static final byte[] ICY_200_OK = "ICY 200 OK\r\n\r\n".getBytes(StandardCharsets.UTF_8);

    private final Socket socket;
    private final PacketPublisher packetPublisher;
    private final int bufferSize;

    ProducerHandler(Socket socket, PacketPublisher packetPublisher, int bufferSize) {
        this.socket = socket;
        this.packetPublisher = packetPublisher;
        this.bufferSize = bufferSize;
    }

    @Override
    public void handle() {
        try (OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream(), bufferSize);
             InputStream inputStream = new BufferedInputStream(socket.getInputStream(), bufferSize)) {
            boolean isInitialised = init(inputStream, outputStream);
            int len;
            byte[] b;
            while (isInitialised && !Thread.currentThread().isInterrupted()) {
                b = new byte[bufferSize];
                if ((len = inputStream.read(b)) != -1) {
                    packetPublisher.sendPacket(new Packet(b, len));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("PH", "exception in handle", e);
        }
    }

    private boolean init(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String data = new Scanner(inputStreamReader).useDelimiter(DELIMITER).next();
        if(data.contains(SOURCE)) {
            outputStream.write(ICY_200_OK);
            outputStream.flush();
            return true;
        } else {
            return false;
        }
    }
}

