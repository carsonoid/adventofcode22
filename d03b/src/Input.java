import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input {
    List<String> all;
    List<String> c1;
    List<String> c2;

    public Input(List<String> c1, List<String> c2) {
        all = new ArrayList<>(c1);
        all.addAll(c2);

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
        return String.format("%s", this.all);
    }

    public String FindDuplicates() {
        for (String c : this.c1) {
            if (this.c2.contains(c)) {
                return c;
            }
        }

        throw new RuntimeException("No duplicates found");
    }

    public String FindShared(Input... others) {
        // for each possible character, check if it is in all of the lists
        for (String c1 : this.all) {
            var foundCount = 0;
            for (Input other : others) {
                for (String c2 : other.all) {
                    if (c1.equals(c2)) {
                        foundCount++;
                        break;
                    }
                }
            }
            if (foundCount == others.length) {
                return c1;
            }
        }

        throw new RuntimeException("No shared found");
    }
}
