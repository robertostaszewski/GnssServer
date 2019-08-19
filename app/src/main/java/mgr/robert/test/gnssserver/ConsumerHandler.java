package mgr.robert.test.gnssserver;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConsumerHandler implements Handler {
    private final Socket socket;
    private final BlockingQueue<Packet> packets;
    private final int bufferSize;

    ConsumerHandler(Socket socket, BlockingQueue<Packet> packets, int bufferSize) {
        this.socket = socket;
        this.packets = packets;
        this.bufferSize = bufferSize;
    }

    @Override
    public void handle() {
        try (OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream(), bufferSize)) {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = packets.poll(1, TimeUnit.SECONDS);
                if (packet != null) {
                    outputStream.write(packet.getBytes(), 0, packet.getLen());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
