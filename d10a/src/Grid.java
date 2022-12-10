import java.util.List;

public class Grid {
    private int[][] grid;
    private int rows;
    private int cols;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
    }

    public Grid(List<String> lines) {
        this(lines.size(), lines.get(0).length());

        for (var y = 0; y < rows; y++) {
            var line = lines.get(y);
            for (var x = 0; x < cols; x++) {
                var value = Character.getNumericValue(line.charAt(x));
                setCell(y, x, value);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCell(int x, int y) {
        return grid[x][y];
    }

    public void setCell(int x, int y, int value) {
        grid[y][x] = value;
    }

    public void Walk(GridWalker walker) {
        for (var y = 0; y < this.rows; y++) {
            for (var x = 0; x < this.cols; x++) {
                walker.HandleCell(x, y, getCell(x, y));
            }
        }
    }

    public void Print() {
        for (var y = 0; y < this.rows; y++) {
            for (var x = 0; x < this.cols; x++) {
                System.out.print(this.getCell(x, y));
            }
            System.out.println();
        }
    }
}
