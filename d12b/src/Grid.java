import java.util.ArrayList;
import java.util.List;

interface GridCellMaker<T> {
    public T From(int X, int Y, String val);
}

public class Grid<T extends GridCell> implements Iterable<T> {
    // multi-dimensional array of List[y]s[x] to enable effecient drawing
    private List<List<T>> grid;
    private int rows;
    private int cols;

    private Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new ArrayList<>();
    }

    public Grid(List<String> lines, GridCellMaker<T> entry) {
        this(lines.size(), lines.get(0).length());

        for (var y = 0; y < rows; y++) {
            var line = lines.get(y);
            List<T> row = new ArrayList<>();
            for (var x = 0; x < cols; x++) {
                var c = line.charAt(x);
                var value = String.valueOf(c);
                row.add(entry.From(x, y, value));
            }
            this.grid.add(row);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public T getCell(int x, int y) {
        return this.grid.get(y).get(x);
    }

    public List<T> getCardinalNeighbors(int x, int y) {
        var l = new ArrayList<T>();
        // left
        if (x >= 1) {
            l.add(this.getCell(x - 1, y));
        }

        // right
        if (x < this.cols - 1) {
            l.add(this.getCell(x + 1, y));
        }

        // up
        if (y >= 1) {
            l.add(this.getCell(x, y - 1));
        }

        // down
        if (y < this.rows - 1) {
            l.add(this.getCell(x, y + 1));
        }

        return l;
    }

    public void setCell(int x, int y, T value) {
        grid.get(y).set(x, value);
    }

    public GridIterator<T> iterator() {
        return new GridIterator<T>(this);
    }

    public void Print() {
        for (var row : this.grid) {
            for (var cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}
