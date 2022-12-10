import java.util.Arrays;
import java.util.List;

public class CPUInstruction {
    String op;
    List<String> args;
    Integer numCycles;

    public CPUInstruction(String op, List<String> args) {
        this.op = op;
        this.args = args;

        switch (this.op) {
            case "noop":
                this.numCycles = 1;
                break;
            case "addx":
                this.numCycles = 2;
                break;
            default:
                throw new RuntimeException("unknown operation");
        }
    }

    public static CPUInstruction FromLine(String CPUInstruction) {
        String[] parts = CPUInstruction.split(" ");

        return new CPUInstruction(
                parts[0],
                Arrays.asList(parts).subList(1, parts.length));
    }

    public String toString() {
        return String.format("%s %s", this.op, this.args);
    }
}
