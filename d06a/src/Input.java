import java.util.ArrayList;
import java.util.List;

public class Input {
    List<Character> data;

    public Input() {
    }

    public Input(List<Character> data) {
        this.data = data;
    }

    public static Input FromLine(String input) {
        List<Character> data = new ArrayList<>();
        for (char c : input.toCharArray()) {
            data.add(c);
        }
        return new Input(data);
    }

    public String toString() {
        return String.format("%s", this.data);
    }
}
