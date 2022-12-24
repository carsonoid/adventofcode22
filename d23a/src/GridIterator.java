import java.util.Iterator;

public class GridIterator implements Iterator<GridElement> {
    MapGrid grid;
    Integer y;
    Integer x;

    Integer lastx;
    Integer lasty;
    Node next = null;

    public GridIterator(MapGrid grid) {
        this.grid = grid;

        this.x = this.grid.xmin;
        this.y = this.grid.ymin;
    }

    public boolean hasNext() {
        while (next == null && this.y <= this.grid.ymax) {
            lastx = this.x;
            lasty = this.y;
            next = this.grid.GetNode(lastx, lasty);
            this.x++;

            if (this.x > this.grid.xmax) {
                this.x = this.grid.xmin;
                this.y++;
            }
        }

        return next != null;
    }

    public GridElement next() {
        var node = this.next;
        this.next = null;
        return new GridElement(lastx, lasty, node);
    }
}
