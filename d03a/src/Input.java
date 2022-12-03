import java.util.Arrays;
import java.util.List;

public class Input {
    List<String> c1;
    List<String> c2;

    public Input(List<String> c1, List<String> c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public static Input FromLine(String input) {
        // conver the input to a List
        List<String> inputList = Arrays.asList(input.split(""));

        return new Input(
                inputList.subList(0, inputList.size() / 2),
                inputList.subList(inputList.size() / 2, inputList.size()));
    }

    public String toString() {
        return String.format("c1: %s, c2: %s", this.c1, this.c2);
    }

    public String FindDuplicates() {
        for (String c : this.c1) {
            if (this.c2.contains(c)) {
                return c;
            }
        }

        throw new RuntimeException("No duplicates found");
    }
}
