import java.util.ArrayList;
import java.util.List;

public class CommandExecution {
    String command;
    List<String> args;
    List<String> results;

    public CommandExecution(String line) {
        var parts = line.split(" ");
        this.command = parts[1];
        this.args = new ArrayList<>();
        for (int i = 2; i < parts.length; i++) {
            this.args.add(parts[i]);
        }
        this.results = new ArrayList<>();
    }

    public void addResult(String line) {
        this.results.add(line);
    }

    public String toString() {
        return String.format("%s %s %s", this.command, this.args, this.results);
    }
}
