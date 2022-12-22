public class LavaCell extends GridCell {
    char value;

    public LavaCell(int X, int Y, int Z, char value) {
        super(X, Y, Z);
        this.value = value;
    }

    public LavaCell(String X, String Y, String Z, char value) {
        super(Integer.parseInt(X), Integer.parseInt(Y), Integer.parseInt(Z));
        this.value = value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
