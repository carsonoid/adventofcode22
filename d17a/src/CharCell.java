public class CharCell extends GridCell {
    char value;

    public CharCell(char value) {
        this.value = value;
    }

    public CharCell(String value) {
        this.value = value.toCharArray()[0];
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
