import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        var jetMaker = new JetMaker(args[0]);
        var rockMaker = new RockMaker(System.getProperty("user.dir") + "/input/rocks.txt");

        Chamber chamber = new Chamber();

        var rocksFallen = 0;
        Rock rock = null;
        var fallUntil = Integer.MAX_VALUE;
        var cycleEndHeight = 0;
        while (fallUntil > 0) {
            if (rock == null) {
                rock = rockMaker.Make();
                rock.SetTo(2, chamber.Start());
            }

            rock.Move(jetMaker.Make(), chamber);

            var moved = rock.Move('v', chamber);
            if (!moved) {
                chamber.AddRock(rock);
                // chamber.Print();
                rock = null;
                rocksFallen++;
                fallUntil--;

                // chamber.Print();

                if (heightAfterCycles == 0) {
                    var numLeft = CheckCycle(chamber, jetMaker.pos, rockMaker.pos, rocksFallen);
                    if (numLeft > 0) {
                        fallUntil = numLeft;
                        cycleEndHeight = chamber.Height();
                    }
                }
            }
        }
        System.out.println(heightAfterCycles + (chamber.Height() - cycleEndHeight));
    }

    static class CycleStats {
        public int rockCount;
        public int chamberHeight;

        public CycleStats(int rockCount, int heightIncrease) {
            this.rockCount = rockCount;
            this.chamberHeight = heightIncrease;
        }
    }

    static long heightAfterCycles = 0;

    private static Map<String, CycleStats> cycleSet = new HashMap<>();

    private static int CheckCycle(Chamber chamber, int jetPos, int rockPos, int rockCount) {
        String key = chamber.Surface() + "-" + jetPos + rockPos;

        if (cycleSet.containsKey(key)) {
            var first = cycleSet.get(key);
            // cycle starts at the height of the object found
            System.out.println("Cycle found!");
            System.out.println("Start:\t" + (first.chamberHeight));
            var rocksPerCycle = rockCount - first.rockCount;
            System.out.println("Rocks/cycle:\t" + rocksPerCycle);

            var heightIncreasePerCycle = chamber.Height() - first.chamberHeight;
            System.out.println("Height/cycle:\t" + heightIncreasePerCycle);

            // this means that for every cycle, we add rocksPerCycle number of rocks
            // so to get to 1e12, we need to add 1e12 / rocksPerCycle
            var heightRemaining = 1000000000000L - chamber.Height();
            var cyclesTo1e12 = heightRemaining / rocksPerCycle;
            System.out.println("Cycles to 1e12:\t" + cyclesTo1e12);

            // add the height increase per cycle to the current height
            var heightAt1e12 = chamber.Height() + (cyclesTo1e12 * heightIncreasePerCycle);
            System.out.println("Height at 1e12:\t" + heightAt1e12);

            heightAfterCycles = heightAt1e12;

            var rocksDropped = cyclesTo1e12 * rocksPerCycle + rockCount;
            System.out.println("Rocks dropped:\t" + rocksDropped);

            var rocksRemaining = 1000000000000L - rocksDropped;
            System.out.println("Rocks remaining:\t" + rocksRemaining);

            return Integer.parseInt(String.valueOf(rocksRemaining));
        } else {
            cycleSet.put(key, new CycleStats(rockCount, chamber.Height()));
        }

        return 0;
    }
}
