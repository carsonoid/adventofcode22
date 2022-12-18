import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RockMaker {
    List<Rock> rocks = new ArrayList<>();
    int pos = 0;

    public RockMaker(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        List<String> rockLines = new ArrayList<>();
        for (var line : lines) {
            if (line.isEmpty()) {
                this.rocks.add(new Rock(rockLines));
                rockLines.clear();
            } else {
                rockLines.add(line);
            }
        }
        this.rocks.add(new Rock(rockLines));
    }

    public Rock Make() {
        var next = rocks.get(this.pos);
        this.pos++;
        if (this.pos == this.rocks.size()) {
            this.pos = 0;
        }
        return next;
    }

    public String toString() {
        return String.valueOf(this.rocks);
    }
}
