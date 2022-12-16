import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class App {
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

            sensorPackages.add(new SensorPackage(sensor, beacon, dist));
        }

        // var search = 20;
        var search = 4000000;

        for (var p : sensorPackages) {
            // System.out.printf("test sensor: %s - %d\n", p.sensor, p.dist);

            var min = -p.dist - 1;
            var max = p.dist + 1;

            for (var i = min; i < max; i++) {
                var y = p.sensor.Y + i;

                if (y < 0 || y > search) {
                    break;
                }

                List<Integer> xchoices = new ArrayList<>();
                xchoices.add(p.sensor.X - Math.abs(p.dist + 1 - i));
                xchoices.add(p.sensor.X + Math.abs(p.dist + 1 - i));

                for (var x : xchoices) {
                    if (x < 0 || x > search) {
                        break;
                    }

                    var match = true;

                    for (var other : sensorPackages) {
                        // System.out.println(" against sensor: " + other.sensor);
                        var diff = mdist(other.sensor, new GridCell(x, y));
                        if (diff <= other.dist) {
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        long result = x;
                        result = result * 4000000 + y;
                        System.out.println(result);
                        System.exit(0);
                    }
                }
            }
        }
    }

    public static int mdist(GridCell a, GridCell b) {
        return Math.abs(a.X - b.X) + Math.abs(a.Y - b.Y);
    }
}
