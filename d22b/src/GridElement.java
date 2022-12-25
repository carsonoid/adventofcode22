public class GridElement {
    Integer x, y;
    Node value;
    public Character dirChange;

    public GridElement(Integer x, Integer y, Node value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.dirChange = ' ';
    }

    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }
}
