public class MoveInstruction {
    Character dir;
    Integer dist;

    public MoveInstruction(Character dir, Integer len) {
        this.dir = dir;
        this.dist = len;
    }

    public static MoveInstruction FromLine(String MoveInstruction) {
        String[] parts = MoveInstruction.split(" ");
        return new MoveInstruction(
                parts[0].toCharArray()[0],
                Integer.parseInt(parts[1]));
    }

    public String toString() {
        return String.format("%s %d", this.dir, this.dist);
    }
}
