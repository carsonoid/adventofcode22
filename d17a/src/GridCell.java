public class GridCell {
    public int X;
    public int Y;

    public GridCell() {
    }

    public GridCell(String X, String Y) {
        this(Integer.parseInt(X), Integer.parseInt(Y));
    }

    public GridCell(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public void setCoord(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public String coordString() {
        return String.format("(%d,%d)", this.X, this.Y);
    }

    public String toString() {
        return this.coordString();
    }
}
