import java.util.List;

public class CPU {
    Integer X;
    Integer cycle;
    CPUCycleInspector inspector;

    public CPU() {
        this.X = 1;
        this.cycle = 1;
    }

    public void AddInspector(CPUCycleInspector inspector) {
        this.inspector = inspector;
    }

    public void Run(List<CPUInstruction> instructions, Integer stopAt) {
        var instructionIter = instructions.iterator();
        var instruction = instructionIter.next();
        var nextAfter = instruction.numCycles;
        for (int cycle = 1; cycle <= stopAt; cycle++) {
            // System.out.printf("Cycle %s START | ", cycle);
            // System.out.printf("X: %d | ", X);

            this.inspector.HandleCycle(cycle, this);

            // System.out.printf("DO %s ", instruction);

            nextAfter--;
            if (nextAfter == 0) {
                switch (instruction.op) {
                    case "noop":
                        break;
                    case "addx":
                        if (nextAfter == 0) {
                            Integer val = Integer.parseInt(instruction.args.get(0));
                            X += val;
                        }
                        break;
                    default:
                        throw new RuntimeException("unknown operation");
                }
                if (instructionIter.hasNext()) {
                    instruction = instructionIter.next();
                    nextAfter = instruction.numCycles;
                }
            }
            // System.out.printf(" Cycle %s END |", cycle);
            // System.out.printf("X: %d\n", X);
        }
    }

    public String toString() {
        return String.format("X: %d", this.X);
    }
}
