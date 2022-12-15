import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    static Grid<SandCell> grid;

    static int abyssStartsAt;

    // test2 values
    // static int sourceX = 16;
    // static int sourceY = -1;
    // static int gridRows = 12;
    // static int gridCols = 30;

    // real values
    static int sourceX = 500;
    static int sourceY = -1;
    static int gridRows = 1000;
    static int gridCols = 1000;

    public static void main(String[] args) throws Exception {
        grid = new Grid<SandCell>(gridRows, gridCols);

        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        for (var line : lines) {
            var parts = line.split(" ");
            var x1 = -1;
            var y1 = -1;
            for (var i = 0; i < parts.length; i += 2) {
                var components = parts[i].split(",");
                var x2 = Integer.parseInt(components[0]);
                var y2 = Integer.parseInt(components[1]);

                // check for "lowest" point by looking for higher
                // y values
                if (y2 > abyssStartsAt) {
                    abyssStartsAt = y2;
                }

                if (x1 >= 0 && y1 >= 0) {
                    // draw lines
                    System.out.printf("draw line from (%d,%d) to (%d,%d)\n", x1, y1, x2, y2);

                    if (x1 == x2) {
                        if (y1 < y2) {
                            // vertical line up
                            for (var y = y1; y <= y2; y++) {
                                grid.setCell(x1, y, new SandCell("#"));
                            }
                        } else {
                            // vertical line down
                            for (var y = y1; y >= y2; y--) {
                                grid.setCell(x1, y, new SandCell("#"));
                            }
                        }
                    } else {
                        if (x1 < x2) {
                            // horizontal line going left
                            for (var x = x2; x >= x1; x--) {
                                grid.setCell(x, y1, new SandCell("#"));
                            }
                        } else {
                            // horizontal line going right
                            for (var x = x2; x <= x1; x++) {
                                grid.setCell(x, y1, new SandCell("#"));
                            }
                        }
                    }
                }
                x1 = x2;
                y1 = y2;
            }
        }

        // grid.Print();
        System.out.println("abyss starts at " + abyssStartsAt);

        // draw a line at the bottom
        System.out.println("floor at " + (abyssStartsAt + 2));
        for (var x = 0; x < grid.getCols(); x++) {
            grid.setCell(x, abyssStartsAt + 2, new SandCell("#"));
        }

        var count = 0;
        try {
            while (true) {
                DropSand(sourceX, sourceY);
                count++;
            }
        } catch (Exception e) {
            // grid.Print();
            System.out.println(e);
        } finally {
            System.out.println("stopped at " + (count + 1));
        }
    }

    static void DropSand(int sandX, int sandY) {
        while (true) {
            if (sandY >= abyssStartsAt + 2) {
                throw new RuntimeException("too far");
            }

            // if cell directly below is empty go down and continue
            var cell = grid.getCell(sandX, sandY + 1);
            if (cell == null) {
                sandY++;
                continue;
            }
            // if cell down and to the left is empty, go there and continue
            cell = grid.getCell(sandX - 1, sandY + 1);
            if (cell == null) {
                sandY++;
                sandX--;
                continue;
            }
            // if cell down and to the right is empty, go there and continue
            cell = grid.getCell(sandX + 1, sandY + 1);
            if (cell == null) {
                sandY++;
                sandX++;
                continue;
            }
            // otherwise we are done
            grid.setCell(sandX, sandY, new SandCell("o"));

            if (sandX == sourceX && sandY == sourceY + 1) {
                throw new RuntimeException("done");
            }

            return;
        }

    }
}
