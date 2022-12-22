import java.util.Iterator;

public class GridIterator<T extends GridCell> implements Iterator<T> {
    Grid<T> grid;
    Integer maxX;
    Integer x;
    Integer maxY;
    Integer y;
    Integer z;
    Integer maxZ;

    public GridIterator(Grid<T> grid) {
        this.grid = grid;
        this.x = 0;
        this.maxX = grid.getCols() - 1;
        this.y = 0;
        this.maxY = grid.getRows() - 1;
        this.z = 0;
        this.maxZ = grid.getDepth() - 1;
    }

    public boolean hasNext() {
        return this.z <= maxZ;
    }

    public T next() {
        var next = this.grid.getCell(this.x, this.y, this.z);

        if (this.x < this.maxX) {
            this.x++;
        } else {
            this.x = 0;
            if (this.y < this.maxY) {
                this.y++;
            } else {
                this.y = 0;
                this.z++;
            }
        }

        return next;
    }
}
