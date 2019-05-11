package mgr.robert.test.gnssserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

public class ServerConn implements Closeable, Readable {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private InputStreamReader inputStreamReader;

    public ServerConn(int port) {
        this.port = port;
    }

    private void init() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(2000);
        socket = serverSocket.accept();
        socket.setTcpNoDelay(true);
        socket.getInputStream();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

    public int read(byte[] b) throws IOException {
        if (inputStream == null) {
            init();
        }
        return inputStream.read(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (outputStream == null) {
            init();
        }
        outputStream.write(b, off, len);
    }

    public void write(byte[] response) throws IOException {
        write(response, 0, response.length);
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (socket != null) {
            socket.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
    }

    @Override
    public int read(CharBuffer cb) throws IOException {
        if (inputStreamReader == null) {
            init();
        }
        return inputStreamReader.read(cb);
    }
}
