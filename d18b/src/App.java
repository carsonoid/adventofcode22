import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // var grid = new Grid<LavaCell>(20, 20, 20);

        // // fill grid with cells
        // for (var z = 0; z < grid.getDepth(); z++) {
        // for (var y = 0; y < grid.getRows(); y++) {
        // for (var x = 0; x < grid.getCols(); x++) {
        // var cell = new LavaCell(x, y, z, '.');
        // grid.setCell(x, y, z, cell);
        // }
        // }
        // }

        // for (String line : lines) {
        // System.out.println(line);
        // var parts = line.split(",");

        // var cell = new LavaCell(parts[0], parts[1], parts[2], '#');

        // grid.setCell(parts[0], parts[1], parts[2], cell);
        // }

        // var result = 0;
        // for (var cell : grid) {
        // if (cell.value != '#') {
        // continue;
        // }

        // var numNeighbors = (int) grid.getCardinalNeighbors(cell.X, cell.Y,
        // cell.Z).stream()
        // .filter(c -> c.value == '#')
        // .count();
        // System.out.printf("%s has\t%d neighbors\n", cell.coordString(),
        // numNeighbors);
        // result += 6 - numNeighbors;
        // }
        // System.out.println(result);

        // for (var cell : grid) {
        // if (cell.value != '.') {
        // continue;
        // }

        // var numNeighbors = (int) grid.getCardinalNeighbors(cell.X, cell.Y,
        // cell.Z).stream()
        // .filter(c -> c.value == '#')
        // .count();
        // if (numNeighbors == 6) {
        // System.out.printf("%s is trapped steam\n", cell.coordString());
        // result -= 6;
        // }
        // }
        // System.out.println(result);

        // invert the input to find steam pockets
        var grid = new Grid<LavaCell>(7, 7, 7);

        // fill grid with cells
        for (var z = 0; z < grid.getDepth(); z++) {
            for (var y = 0; y < grid.getRows(); y++) {
                for (var x = 0; x < grid.getCols(); x++) {
                    var cell = new LavaCell(x, y, z, '#');
                    grid.setCell(x, y, z, cell);
                }
            }
        }

        for (String line : lines) {
            System.out.println(line);
            var parts = line.split(",");

            var cell = new LavaCell(parts[0], parts[1], parts[2], '.');

            grid.setCell(parts[0], parts[1], parts[2], cell);
        }

        grid.PrintLong();

        // var result = 0;
        // for (var cell : grid) {
        // if (cell.value != '.') {
        // continue;
        // }

        // var numNeighbors = (int) grid.getCardinalNeighbors(cell.X, cell.Y,
        // cell.Z).stream()
        // .filter(c -> c.value == '.')
        // .count();
        // System.out.printf("%s has\t%d neighbors\n", cell.coordString(),
        // numNeighbors);
        // result += 6 - numNeighbors;
        // }
        // System.out.println(result);

    }
}
