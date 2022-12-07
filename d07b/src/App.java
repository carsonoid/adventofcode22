import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));

        List<CommandExecution> commandExecutions = new ArrayList<>();
        CommandExecution curCmd = null;
        for (String line : allLines) {
            if (line.startsWith("$ ")) {
                if (curCmd != null) {
                    commandExecutions.add(curCmd);
                }
                curCmd = new CommandExecution(line);
            } else {
                curCmd.addResult(line);
            }
        }
        commandExecutions.add(curCmd);

        DirEntry root = null;
        var fs = root;
        for (CommandExecution cmd : commandExecutions) {
            System.out.println(cmd);
            switch (cmd.command) {
                case "cd":
                    if (fs == null) {
                        root = new DirEntry(null, "dir /");
                        fs = root;
                    } else {
                        fs = fs.CD(cmd.args.get(0));
                    }
                    break;
                case "ls":
                    for (String line : cmd.results) {
                        var entry = new DirEntry(fs, line);
                        fs.Children.put(entry.Name, entry);
                    }
                    break;
            }
        }

        root.PrintAll();

        var sums = new HashMap<String, Integer>();
        GetDirSizes(root, sums);
        System.out.println(sums);

        var totalAvailable = 70000000;
        var need = 30000000;
        var unused = totalAvailable - root.GetSize();
        // figure out which dir deleted gets closest to need
        System.out.println("unused: " + unused);

        var best = totalAvailable;
        for (var entry : sums.entrySet()) {
            var s = entry.getValue();
            if (unused + s >= need && s < best) {
                best = s;
            }
        }
        System.out.println("best: " + best);
    }

    private static void GetDirSizes(DirEntry entry, Map<String, Integer> sums) {
        sums.put(entry.GetPath(), entry.GetSize());
        for (var c : entry.Children.values()) {
            if (c.IsDir) {
                GetDirSizes(c, sums);
            }
        }
    }
}
