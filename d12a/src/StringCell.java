public class StringCell extends GridCell {
    String value;
    Boolean visited;

    public StringCell(int X, int Y, String value) {
        super(X, Y);

        this.value = value;
        this.visited = false;
    }

    public String toString() {
        return this.value;
    }
}
