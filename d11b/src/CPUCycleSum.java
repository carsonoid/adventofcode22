public class CPUCycleSum implements CPUCycleInspector {
    Integer sum;
    Integer nextSumCycle;

    public CPUCycleSum(Integer nextSumCycle) {
        this.sum = 0;
        this.nextSumCycle = nextSumCycle;
    }

    public void HandleCycle(Integer cycleNum, CPU cpu) {
        if (cycleNum.equals(nextSumCycle)) {
            this.sum += (cpu.X * cycleNum);
            this.nextSumCycle += 40;
        }
    }
}
