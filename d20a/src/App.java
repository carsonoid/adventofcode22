import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // read lines into a list of integers
        List<IntPtr> numbers = new ArrayList<>();
        for (var line : lines) {
            numbers.add(new IntPtr(line));
        }

        System.out.println(35 % 9);

        List<IntPtr> data = new ArrayList<>(numbers);

        System.out.println(data);

        for (var i = 0; i < numbers.size(); i++) {
            var moving = numbers.get(i);
            var epos = data.indexOf(moving);
            var elem = data.get(epos);
            // System.out.printf("%s moves\n", elem);

            // if (elem.value == 0) {
            // System.out.println("Found it");
            // }

            var pos = data.indexOf(elem);
            if (pos == -1) {
                throw new Exception("Element not found");
            }

            if (elem.value == -4157) {
                System.out.println("Found it");
            }

            var moveTo = 0;
            if (elem.value > 0) {
                moveTo = (pos + elem.value) % (data.size() - 1);
                moveTo++; // move past the element we're moving to
            } else {
                moveTo = (pos + elem.value) % (data.size() - 1);
            }
            if (moveTo < 0) {
                moveTo = data.size() + moveTo;
            }

            var removeFrom = pos;
            if (moveTo < pos) {
                removeFrom = pos + 1;
            }

            data.add(moveTo, elem);
            // System.out.println(data);
            data.remove(removeFrom);

            // System.out.println(data);
        }

        System.out.println();

        // find the index of 0
        var pos = 0;
        for (var i = 0; i < data.size(); i++) {
            if (data.get(i).value == 0) {
                pos = i;
                break;
            }
        }

        var x = data.get((pos + 1000) % (data.size() - 1));
        var y = data.get((pos + 2000) % (data.size() - 1));
        var z = data.get((pos + 3000) % (data.size() - 1));

        System.out.println(data);

        System.out.printf("%s %s %s\n", x, y, z);
        System.out.printf("%s\n", x.value + y.value + z.value);
    }
}

class IntPtr {
    static char idCounter = 'a';

    public int value;

    // the id isn't needed for uniqueness
    // but it's useful for debugging duplicate ints
    public char id;

    public IntPtr(String value) {
        this.value = Integer.parseInt(value);
        this.id = idCounter++;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
