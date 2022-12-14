public class PacketPair {
    public String Left;
    public String Right;

    public PacketPair(String Left, String Right) {
        this.Left = Left;
        this.Right = Right;
    }

    public boolean IsInOrder() {
        return false;
    }

    public String toString() {
        return String.format("%s vs %s", this.Left, this.Right);
    }
}
