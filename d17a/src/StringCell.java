public class StringCell extends GridCell {
    String value;
    Boolean visited;

    public StringCell(String value) {
        this.value = value;
        this.visited = false;
    }

    public String toString() {
        return this.value;
    }
}
