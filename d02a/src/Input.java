public class Input {
    String them;
    String me;

    public Input(String them, String me) {
        this.them = them;
        this.me = me;
    }

    public static Input FromLine(String input) {
        String[] parts = input.split(" ");
        return new Input(
                parts[0],
                parts[1]);
    }

    public String toString() {
        return String.format("%s vs %s", this.them, this.me);
    }
}
