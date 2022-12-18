import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JetMaker {
    List<Character> jets;
    int pos = 0;

    public JetMaker(String path) throws IOException {
        this.jets = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(path));

        for (var line : lines) {
            for (var c : line.toCharArray()) {
                this.jets.add(c);
            }
        }
    }

    public int Pos() {
        return this.pos;
    }

    public char Make() {
        var next = jets.get(this.pos);
        this.pos++;
        if (this.pos == this.jets.size()) {
            this.pos = 0;
        }
        return next;
    }

    public String toString() {
        return String.valueOf(this.jets);
    }
}
