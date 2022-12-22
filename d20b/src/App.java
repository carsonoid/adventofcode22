import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // read lines into a list of longs
        List<LongPtr> numbers = new ArrayList<>();
        for (var line : lines) {
            numbers.add(new LongPtr(line));
        }

        List<LongPtr> data = new ArrayList<>(numbers);

        System.out.println(data);

        for (long j = 0; j < 10; j++) {
            for (long i = 0; i < numbers.size(); i++) {
                var moving = numbers.get((int) i);
                long epos = data.indexOf(moving);
                var elem = data.get((int) epos);
                // System.out.printf("%s moves\n", elem);

                long pos = data.indexOf(elem);
                if (pos == -1) {
                    throw new Exception("Element not found");
                }

                // This would also work instead of the modulo stuff below
                // but I'm keeping my stuff in.
                // long moveTo = Math.floorMod(pos + elem.value, data.size() - 1);

                var moveTo = (pos + elem.value) % (data.size() - 1);
                if (moveTo < 0) {
                    moveTo = data.size() + moveTo - 1;
                }
                if (moveTo > pos) {
                    moveTo++; // move past the element we're moving to
                }

                var removeFrom = pos;
                if (moveTo < pos) {
                    removeFrom = pos + 1;
                }

                data.add((int) moveTo, elem);
                // System.out.println(data);
                data.remove((int) removeFrom);

                // System.out.println(data);
            }

            System.out.println("After " + (j + 1) + " rounds:");
            System.out.println(data);
            // break;
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

        long x = data.get((pos + 1000) % (data.size())).value;
        long y = data.get((pos + 2000) % (data.size())).value;
        long z = data.get((pos + 3000) % (data.size())).value;

        System.out.printf("%s %s %s\n", x, y, z);
        System.out.printf("%s\n", x + y + z);
    }
}

class LongPtr {
    public long value;

    public LongPtr(String value) {
        this.value = Long.valueOf(value) * 811589153;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
