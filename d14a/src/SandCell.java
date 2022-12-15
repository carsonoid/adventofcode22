public class SandCell extends GridCell {
    char value;

    public SandCell(String value) {
        this.value = value.toCharArray()[0];
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
