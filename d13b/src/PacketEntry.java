public class PacketEntry {
    public boolean IsPacket;
    public Integer data;
    public Packet packet;

    public PacketEntry(Integer i) {
        this.IsPacket = false;
        this.data = i;
    }

    public PacketEntry(Packet p) {
        this.IsPacket = true;
        this.packet = p;
    }

    public String toString() {
        if (this.IsPacket) {
            return this.packet.toString();
        }
        return String.valueOf(this.data);
    }
}
