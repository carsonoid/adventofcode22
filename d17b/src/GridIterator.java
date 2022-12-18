import java.util.Iterator;

public class GridIterator<T extends GridCell> implements Iterator<T> {
    Grid<T> grid;
    Integer maxX;
    Integer x;
    Integer maxY;
    Integer y;

    public GridIterator(Grid<T> grid) {
        this.grid = grid;
        this.x = 0;
        this.maxX = grid.getCols() - 1;
        this.y = 0;
        this.maxY = grid.getRows() - 1;
    }

    public boolean hasNext() {
        return y <= maxY;
    }

    public T next() {
        var next = this.grid.getCell(this.x, this.y);

        if (this.x == maxX) {
            this.x = 0;
            this.y++;
        } else {
            this.x++;
        }

        return next;
    }
}
