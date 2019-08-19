package mgr.robert.test.gnssserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProducerHandler implements Handler {
    private final Socket socket;
    private final PacketPublisher packetPublisher;
    private final int bufferSize;

    public ProducerHandler(Socket socket, PacketPublisher packetPublisher, int bufferSize) {
        this.socket = socket;
        this.packetPublisher = packetPublisher;
        this.bufferSize = bufferSize;
    }

    @Override
    public void handle() {
        try (OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream(), bufferSize);
             InputStream inputStream = new BufferedInputStream(socket.getInputStream(), bufferSize)) {
            init(inputStream, outputStream);
            int len;
            byte[] b;
            while (!Thread.currentThread().isInterrupted()) {
                b = new byte[bufferSize];
                if ((len = inputStream.read(b)) != -1) {
                    packetPublisher.sendPacket(new Packet(b, len));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String data = new Scanner(inputStreamReader).useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^SOURCE").matcher(data);
        System.out.println(data);
        byte[] response = "ICY 200 OK\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        outputStream.write(response);
        outputStream.flush();
    }


}

