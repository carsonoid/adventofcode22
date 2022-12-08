import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));

        var sum = 0;

        Map<String, Integer> map = new HashMap<>();

        // check the number of increases for each line left to right then right to left
        var y = 0;
        for (String line : allLines) {
            var h = -1;
            for (int x = 0; x < line.length(); x++) {
                System.out.printf("L - X:%d,Y:%d\n", x, y);
                var size = Character.getNumericValue(line.charAt(x));
                if (size > h) {
                    h = size;
                    if (!map.containsKey(x + "," + y)) {
                        map.put(x + "," + y, size);
                        sum++;
                    }
                }
            }

            h = -1;
            for (int x = line.length() - 1; x >= 0; x--) {
                System.out.printf("R - X:%d,Y:%d\n", x, y);
                var size = Character.getNumericValue(line.charAt(x));
                if (size > h) {
                    h = size;
                    if (!map.containsKey(x + "," + y)) {
                        map.put(x + "," + y, size);
                        sum++;
                    }
                }
            }

            y++;
        }

        // check the number of increases for each line top to bottom
        for (var x = 0; x < allLines.get(0).length(); x++) {
            var h = -1;
            for (y = 0; y < allLines.size(); y++) {
                System.out.printf("D - X:%d,Y:%d\n", x, y);
                var size = Character.getNumericValue(allLines.get(y).charAt(x));
                if (size > h) {
                    h = size;
                    if (!map.containsKey(x + "," + y)) {
                        map.put(x + "," + y, size);
                        sum++;
                    }
                }
            }
        }

        // check the number of increases for each line bottom to top
        for (var x = 0; x < allLines.get(0).length(); x++) {
            var h = -1;
            for (y = allLines.size() - 1; y >= 0; y--) {
                var size = Character.getNumericValue(allLines.get(y).charAt(x));
                System.out.printf("U - X:%d,Y:%d - %d\n", x, y, size);
                if (size > h) {
                    h = size;
                    if (!map.containsKey(x + "," + y)) {
                        map.put(x + "," + y, size);
                        sum++;
                    }
                }
            }
        }

        List<String> allPositions = new ArrayList<>();
        for (var entry : map.entrySet()) {
            allPositions.add(entry.getKey());
        }

        allPositions.sort(null);

        for (String position : allPositions) {
            System.out.println(position + " " + map.get(position));
        }

        System.out.println(sum);
    }
}
