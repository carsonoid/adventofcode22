import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // var testGrid = new Grid<GridCell>(3, 3, 3);
        // testGrid.setCell(1, 1, 1, new GridCell(1, 1, 1));
        // testGrid.setCell(2, 1, 1, new GridCell(2, 1, 1));
        // testGrid.PrintLong();

        var grid = new Grid<GridCell>(20, 20, 20);

        for (String line : lines) {
            System.out.println(line);
            var parts = line.split(",");
            grid.setCell(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    new GridCell(parts[0], parts[1], parts[2]));
        }

        // grid.PrintLong();

        var result = 0;
        for (var cell : grid) {
            if (cell == null) {
                continue;
            }

            var numNeighbors = (int) grid.getCardinalNeighbors(cell.X, cell.Y,
                    cell.Z).stream()
                    .filter(c -> c != null)
                    .count();
            System.out.printf("%s has\t%d neighbors\n", cell.coordString(),
                    numNeighbors);
            result += 6 - numNeighbors;
        }
        System.out.println(result);
    }
}
