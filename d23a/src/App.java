import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        Integer numElves = 0;
        MapGrid grid = new MapGrid();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                var c = line.charAt(x);
                if (c == '.') {
                    continue;
                }
                grid.AddNode(x, y, new Node(c));
                numElves++;
            }
        }

        System.out.println("Initial grid:");
        grid.Print();

        // all start by proposing N, S ,W ,E in that order
        var checkOrder = new ArrayList<Character>();
        checkOrder.add('N');
        checkOrder.add('S');
        checkOrder.add('W');
        checkOrder.add('E');

        for (var i = 0; i < 10; i++) {
            System.out.println("Round " + (i + 1));

            Map<String, List<GridElement>> moves = new HashMap<>();
            for (GridElement element : grid) {
                // System.out.println(element + " is proposing a move to");
                var coordinate = grid.ProposeMove(element.x, element.y, checkOrder);
                if (coordinate == null) {
                    continue;
                }
                var coordString = coordinate.toString();
                if (!moves.containsKey(coordString)) {
                    moves.put(coordString, new ArrayList<>());
                }
                moves.get(coordString).add(element);
            }

            if (moves.isEmpty()) {
                System.out.println("No moves");
                break;
            }

            // for (var entry : moves.entrySet()) {
            // var coordinate = entry.getKey();
            // var elements = entry.getValue();
            // System.out.println(coordinate + " has " + elements + " elements");
            // }
            // System.out.println("Moves: " + moves);

            // now move all the elves at once, if they are the only one proposing a move to
            // a position
            for (var entry : moves.entrySet()) {
                var coordinate = entry.getKey();
                var elements = entry.getValue();
                if (elements.size() == 1) {
                    // System.out.println("Moving " + elements.get(0) + " to " + coordinate);
                    var element = elements.get(0);
                    var coord = new Coordinate(coordinate);
                    grid.Move(element.x, element.y, coord.x, coord.y);
                }
            }

            // move the first check to the end
            checkOrder.add(checkOrder.remove(0));

            // grid.Print();
        }

        var w = grid.GetWidth();
        var h = grid.GetHeight();
        var total = w * h;
        total -= numElves;
        System.out.println("Answer: " + total);
    }
}
