import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        List<MoveInstruction> moves = allLines.stream().map(MoveInstruction::FromLine).collect(Collectors.toList());

        // var h = new RopePoint(2, 2);
        // var t = new RopePoint(1, 1);
        // h.AddChild(t);
        // h.Move('R');
        // h.Print();

        var head = new RopePoint(0, 0);
        var tail = new RopePoint(0, 0);
        head.AddChild(tail);

        for (var move : moves) {
            head.Move(move);
        }

        System.out.println(tail.visited.size());
    }
}
