import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));

        // System.out.println(getScore(allLines, 2, 3));
        // System.exit(0);

        var best = 0;

        // check the number of increases for each line left to right then right to left
        var y = 0;
        for (String line : allLines) {
            var h = -1;
            for (int x = 0; x < line.length(); x++) {
                var score = getScore(allLines, x, y);
                System.out.printf("Testing - X:%d,Y:%d - score: %d\n", x, y, score);
                if (score > best) {
                    best = score;
                }
            }

            y++;
        }

        System.out.println(best);
    }

    public static Integer getScore(List<String> allLines, int startX, int startY) {
        var minX = 0;
        var maxX = allLines.get(0).length() - 1;
        var minY = 0;
        var maxY = allLines.size() - 1;
        var curSize = Character.getNumericValue(allLines.get(startY).charAt(startX));

        // check left to right
        var lookRight = 0;
        for (var x = startX + 1; x <= maxX; x++) {
            var size = Character.getNumericValue(allLines.get(startY).charAt(x));
            lookRight++;
            if (size >= curSize) {
                break;
            }
        }

        // check right to left
        var lookLeft = 0;
        for (var x = startX - 1; x >= minX; x--) {
            var size = Character.getNumericValue(allLines.get(startY).charAt(x));
            lookLeft++;
            if (size >= curSize) {
                break;
            }
        }

        // check top to bottom
        var lookDown = 0;
        for (var y = startY + 1; y <= maxY; y++) {
            var size = Character.getNumericValue(allLines.get(y).charAt(startX));
            lookDown++;
            if (size >= curSize) {
                break;
            }
        }

        // check bottom to top
        var lookUp = 0;
        for (var y = startY - 1; y >= minY; y--) {
            var size = Character.getNumericValue(allLines.get(y).charAt(startX));
            lookUp++;
            if (size >= curSize) {
                break;
            }
        }

        System.out.printf("U %d, L %d, D %d, R %d\n", lookUp, lookLeft, lookDown, lookRight);

        return lookLeft * lookRight * lookUp * lookDown;
    }
}
