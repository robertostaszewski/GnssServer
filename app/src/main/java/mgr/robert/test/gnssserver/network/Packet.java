package mgr.robert.test.gnssserver.network;

import java.util.Arrays;

public class Packet {
    private final byte[] bytes;
    private final int len;

    public Packet(byte[] bytes, int len) {
        this.bytes = bytes;
        this.len = len;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getLen() {
        return len;
    }

    public static Packet from(Packet packet) {
        return new Packet(packet.bytes, packet.len);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return len == packet.len &&
                Arrays.equals(bytes, packet.bytes);
    }
}
