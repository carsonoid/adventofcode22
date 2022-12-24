public class GridElement {
    Integer x, y;
    Node value;

    public GridElement(Integer x, Integer y, Node value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public String toString() {
        return String.format("(%d,%d) %s", this.x, this.y, this.value);
    }
}
