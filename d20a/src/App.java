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

        List<IntPtr> data = new ArrayList<>(numbers);

        System.out.println(data);

        for (var i = 0; i < numbers.size(); i++) {
            var moving = numbers.get(i);
            var epos = data.indexOf(moving);
            var elem = data.get(epos);
            // System.out.printf("%s moves\n", elem);

            var pos = data.indexOf(elem);
            if (pos == -1) {
                throw new Exception("Element not found");
            }

            var moveTo = (pos + elem.value) % (data.size() - 1);
            if (moveTo > pos) {
                moveTo++; // move past the element we're moving to
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

        var x = data.get((pos + 1000) % (data.size()));
        var y = data.get((pos + 2000) % (data.size()));
        var z = data.get((pos + 3000) % (data.size()));

        System.out.println(data);

        System.out.printf("%s %s %s\n", x, y, z);
        System.out.printf("%s\n", x.value + y.value + z.value);
    }
}

class IntPtr {
    public int value;

    public IntPtr(String value) {
        this.value = Integer.parseInt(value);
    }

    public String toString() {
        return String.valueOf(value);
    }
}
