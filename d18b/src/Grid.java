import java.util.ArrayList;
import java.util.List;

interface GridCellMaker<T> {
    public T From(int X, int Y, int Z, String val);
}

public class Grid<T extends GridCell> implements Iterable<T> {
    // multi-dimensional array of List[z][y][x]
    private List<List<List<T>>> grid;
    private int rows;
    private int cols;
    private int depth;

    public Grid(int rows, int cols, int depth) {
        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.grid = new ArrayList<>();
        for (var z = 0; z < depth; z++) {
            var layer = new ArrayList<List<T>>();
            for (var y = 0; y < rows; y++) {
                var row = new ArrayList<T>();
                for (var x = 0; x < cols; x++) {
                    row.add(null);
                }
                layer.add(row);
            }
            this.grid.add(layer);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getDepth() {
        return depth;
    }

    public T getCell(int x, int y, int z) {
        return this.grid.get(z).get(y).get(x);
    }

    public List<T> getCardinalNeighbors(int x, int y, int z) {
        var l = new ArrayList<T>();
        // left
        if (x >= 1) {
            l.add(this.getCell(x - 1, y, z));
        }

        // right
        if (x < this.cols - 1) {
            l.add(this.getCell(x + 1, y, z));
        }

        // up
        if (y >= 1) {
            l.add(this.getCell(x, y - 1, z));
        }

        // down
        if (y < this.rows - 1) {
            l.add(this.getCell(x, y + 1, z));
        }

        // front
        if (z >= 1) {
            l.add(this.getCell(x, y, z - 1));
        }

        // back
        if (z < this.depth - 1) {
            l.add(this.getCell(x, y, z + 1));
        }

        return l;
    }

    public void setCell(int x, int y, int z, T value) {
        value.setCoord(x, y, z);
        grid.get(z).get(y).set(x, value);
    }

    public void setCell(String sx, String sy, String sz, T value) {
        var x = Integer.parseInt(sx);
        var y = Integer.parseInt(sy);
        var z = Integer.parseInt(sz);
        this.setCell(x, y, z, value);
    }

    public GridIterator<T> iterator() {
        return new GridIterator<T>(this);
    }

    public void Print() {
        for (var z = 0; z < this.depth; z++) {
            System.out.printf(" Z:%d\n", z);
            for (var y = 0; y < this.rows; y++) {
                System.out.printf("   ");
                for (var x = 0; x < this.cols; x++) {
                    var cell = this.getCell(x, y, z);
                    if (cell == null) {
                        System.out.print(" ");
                    } else {
                        System.out.print("#");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    // PrintLong() prints the 3d space with z layers spread across the terminal
    public void PrintLong() {
        System.out.printf(" Z:");
        for (var z = 0; z < this.depth; z++) {
            System.out.print("|");
            for (var x = 0; x < this.cols; x++) {
                if (x == this.cols / 2) {
                    System.out.printf("%d", z);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
        }
        System.out.println();

        System.out.printf("   ");
        for (var z = 0; z < this.depth; z++) {
            System.out.print("+");
            for (var x = 0; x < this.cols; x++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();

        System.out.printf(" X:");
        for (var z = 0; z < this.depth; z++) {
            System.out.print("|");
            for (var x = 0; x < this.cols; x++) {
                System.out.print(x);
            }
            System.out.print("|");
        }
        System.out.println();

        System.out.printf("   ");
        for (var z = 0; z < this.depth; z++) {
            System.out.print("+");
            for (var x = 0; x < this.cols; x++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();

        for (var y = 0; y < this.rows; y++) {
            System.out.printf(" %d:", y);
            for (var z = 0; z < this.depth; z++) {
                System.out.print("|");
                for (var x = 0; x < this.cols; x++) {
                    var cell = this.getCell(x, y, z);
                    if (cell == null) {
                        System.out.print(" ");
                    } else {
                        System.out.print(cell);
                    }
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
