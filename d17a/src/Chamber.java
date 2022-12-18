import java.util.ArrayList;
import java.util.List;

public class Chamber {
    // chamber is 7 wide
    public static final int WIDTH = 7;

    private List<CharCell[]> cells = new ArrayList<>();

    public Chamber() {
    }

    public CharCell GetCell(int x, int y) {
        return this.cells.get(y)[x];
    }

    public int Height() {
        return this.cells.size();
    }

    // rocks start 3 rows above the floor or highest rock
    public int Start() {
        return this.cells.size() + 3;
    }

    // top is the highest rock or floor
    public int Top() {
        return this.cells.size();
    }

    public void Grow(int numRows) {
        for (var i = 0; i < numRows; i++) {
            var row = new CharCell[WIDTH];
            // fill with empty cells
            for (var x = 0; x < WIDTH; x++) {
                row[x] = new CharCell('.');
            }
            this.cells.add(row);
        }
    }

    public void AddRock(Rock r) {
        var maxHeight = r.maxHeight();

        // add rows as needed
        var rowsToAdd = maxHeight - this.cells.size();
        this.Grow(rowsToAdd);

        // set rock cells in chamber
        for (var row : r.cells) {
            for (var cell : row) {
                if (cell.value != '.') {
                    if (this.cells.get(cell.Y)[cell.X].value != '.') {
                        System.out.println("Rock collision!");
                        throw new RuntimeException();
                    }
                    this.cells.get(cell.Y)[cell.X] = cell;
                }
            }
        }
    }

    public void Print() {
        for (var i = this.cells.size() - 1; i >= 0; i--) {
            System.out.print("|");
            var row = this.cells.get(i);
            for (var cell : row) {
                System.out.print(cell);
            }
            System.out.print("|");
            System.out.println();
        }

        // print rows in reverse order
        System.out.print("+");
        for (var i = 0; i < WIDTH; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
