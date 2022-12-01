public class Input {
    String name;
    int value;

    public Input() {
    }

    public Input(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static Input FromLine(String input) {
        String[] parts = input.split(" ");
        return new Input(
                parts[0],
                Integer.parseInt(parts[1]));
    }

    public String toString() {
        return String.format("%s %d", this.name, this.value);
    }
}
