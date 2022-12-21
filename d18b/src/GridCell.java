public class GridCell {
    public int X;
    public int Y;
    public int Z;

    public GridCell() {
    }

    public GridCell(String X, String Y, String Z) {
        this(Integer.parseInt(X), Integer.parseInt(Y), Integer.parseInt(Z));
    }

    public GridCell(int X, int Y, int Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public void setCoord(int X, int Y, int Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public String coordString() {
        return String.format("(%d,%d,%d)", this.X, this.Y, this.Z);
    }

    public String toString() {
        return "#";
    }
}
