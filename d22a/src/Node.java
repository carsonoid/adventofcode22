public class Node {
    public Character Value;

    public Node(Character value) {
        this.Value = value;
    }

    public String toString() {
        return String.format("%s", this.Value);
    }
}
