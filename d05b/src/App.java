import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
    private static final int ArrayList = 0;

    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<String> stacksInput = new ArrayList<String>();
        List<String> movesInput = new ArrayList<String>();
        var stacksDone = false;
        for (var line : allLines) {
            if (line.isEmpty()) {
                stacksDone = true;
                continue;
            }
            if (stacksDone) {
                movesInput.add(line);
            } else {
                stacksInput.add(line);
            }
        }

        System.out.println("stacks " + stacksInput);
        System.out.println("moves " + movesInput);

        var stackIndex = stacksInput.get(stacksInput.size() - 1);

        var stacks = new ArrayList<Stack>();
        var newStack = new Stack();
        // iterate stackIndex one char at a time
        for (var i = 0; i < stackIndex.length(); i++) {
            var c = stackIndex.charAt(i);
            if (c == ' ') {
                if (newStack.crates.size() > 0) {
                    stacks.add(newStack);
                    newStack = new Stack();
                }
            } else {
                for (var stack : stacksInput) {
                    var crateVal = stack.charAt(i);
                    if (crateVal != ' ' && crateVal > '9') {
                        newStack.Add(crateVal);
                    }
                }
            }
        }

        System.out.println("PARSED");
        printStacks(stacks);
        System.out.println("---");

        Pattern expr = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

        for (var move : movesInput) {
            var matcher = expr.matcher(move);
            if (!matcher.matches()) {
                throw new Exception("invalid move: " + move);
            }

            var amount = Integer.parseInt(matcher.group(1));

            // src and dest need to start from zero to make things easier
            var src = Integer.parseInt(matcher.group(2)) - 1;
            var dst = Integer.parseInt(matcher.group(3)) - 1;

            System.out.printf("move %s from %s to %s\n", amount, src, dst);

            // do move
            var srcStack = stacks.get(src);
            var taken = srcStack.Take(amount);
            System.out.println("taken: " + taken);

            var dstStack = stacks.get(dst);
            dstStack.PlaceKeepOrder(taken);

            printStacks(stacks);
        }

        System.out.print("Message: ");
        for (var stack : stacks) {
            if (!stack.crates.isEmpty()) {
                System.out.print(stack.crates.get(0));
            }
        }
        System.out.println();
    }

    private static void printStacks(List<Stack> stacks) {
        for (var i = 0; i < stacks.size(); i++) {
            var stack = stacks.get(i);
            System.out.printf("stack %d: %s\n", i + 1, stack);
        }
    }
}
