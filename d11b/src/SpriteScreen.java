public class SpriteScreen implements CPUCycleInspector {
    int cursorPos;

    public SpriteScreen() {
        this.cursorPos = 0;
    }

    public void HandleCycle(Integer cycleNum, CPU cpu) {
        if (this.cursorPos >= (cpu.X - 1) &&
                (this.cursorPos <= (cpu.X + 1))) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }

        this.cursorPos++;

        // new line every 40 cycles
        if (cycleNum % 40 == 0) {
            System.out.println();
            this.cursorPos = 0;
        }
    }
}
