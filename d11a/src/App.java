import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        List<Monkey> monkeys = new ArrayList<>();
        for (var x = 0; x < lines.size();) {
            var next = x + 7;
            if (lines.size() <= x + 5) {
                break;
            }

            var monkey = new Monkey(lines.subList(x, next - 1));
            monkeys.add(monkey);
            x = next;
        }

        for (var i = 0; i < 20; i++) {
            for (var monkey : monkeys) {
                while (true) {
                    var item = monkey.InspectNext();
                    if (item == -1) {
                        break;
                    }
                    if (item % monkey.testDivisor == 0) {
                        monkeys.get(monkey.trueDest).items.add(item);
                    } else {
                        monkeys.get(monkey.falseDest).items.add(item);
                    }
                }
            }

            System.out.println("Round " + i);
            for (var monkey : monkeys) {
                System.out.println(monkey.items);
            }
        }

        List<Integer> counts = new ArrayList<>();
        for (var monkey : monkeys) {
            counts.add(monkey.inspectionCount);
        }
        counts.sort(null);
        System.out.println(counts.get(counts.size() - 1) * counts.get(counts.size() - 2));
    }
}
