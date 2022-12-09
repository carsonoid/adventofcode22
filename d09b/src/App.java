import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        List<MoveInstruction> moves = allLines.stream().map(MoveInstruction::FromLine).collect(Collectors.toList());

        var head = new RopePoint(0, 0);

        for (var i = 0; i < 9; i++) {
            head.Tail().AddChild(new RopePoint(0, 0));
        }

        for (var move : moves) {
            head.Move(move);
        }

        var tail = head.Tail();
        System.out.println(tail.visited.size());
    }
}
