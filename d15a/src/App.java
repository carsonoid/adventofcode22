import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class App {
    static Grid<CharCell> grid;
    static int minY = 0;
    static int maxY = 0;
    static int maxX = 0;
    static int minX = 0;

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        Map<String, Boolean> takenSpots = new HashMap<>();

        List<SensorPackage> sensorPackages = new ArrayList<>();
        for (var line : lines) {
            var p = Pattern
                    .compile("Sensor at x=([0-9-]+), y=([0-9-]+): closest beacon is at x=([0-9-]+), y=([0-9-]+)");
            var m = p.matcher(line);
            if (!m.find()) {
                throw new RuntimeException("not matched");
            }
            var sensor = new GridCell(m.group(1), m.group(2));
            var beacon = new GridCell(m.group(3), m.group(4));

            takenSpots.put(sensor.coordString(), true);
            takenSpots.put(beacon.coordString(), true);

            var dist = mdist(sensor, beacon);
            System.out.printf("%s, %s - %d\n", sensor, beacon, dist);

            expandWith(sensor, dist);

            sensorPackages.add(new SensorPackage(sensor, beacon, dist));
        }

        System.out.printf("X: %d to %d\n", minX, maxX);
        System.out.printf("Y: %d to %d\n", minY, maxY);

        var checkY = 2000000;

        var scannedPoints = 0;
        for (var x = minX; x <= maxX; x++) {
            for (var p : sensorPackages) {
                var cell = new GridCell(x, checkY);
                var touches = (mdist(p.sensor, cell) <= p.dist);
                if (touches && !takenSpots.containsKey(cell.coordString())) {
                    // make sure that there is no sensor or beacon at the poin
                    // System.out.printf("%s touches %d,%d\n", p.beacon, x, checkY);
                    scannedPoints++;
                    break;
                }
            }
        }
        System.out.println("scanned: " + scannedPoints);
    }

    public static void expandWith(GridCell c, int dist) {
        minX = Math.min(c.X - dist, minX);
        maxX = Math.max(c.X + dist, maxX);
        minY = Math.min(c.Y - dist, minY);
        maxY = Math.max(c.Y + dist, maxY);
    }

    public static int mdist(GridCell a, GridCell b) {
        return Math.abs(a.X - b.X) + Math.abs(a.Y - b.Y);
    }
}
