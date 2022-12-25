import java.util.Iterator;

public class GridIterator implements Iterator<GridElement> {
    Grid grid;
    Integer y = 0;
    Integer x = 0;

    Node next = null;

    public GridIterator(Grid grid) {
        this.grid = grid;
    }

    public boolean hasNext() {
        return y < this.grid.Height();
    }

    public GridElement next() {
        var node = this.grid.GetNode(x, y);
        var elem = new GridElement(x, y, node);

        if (x == this.grid.Width() - 1) {
            x = 0;
            y++;
        } else {
            x++;
        }

        return elem;
    }
}
