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

        var root = new DirEntry(null, "dir /");
        var fs = root;
        for (CommandExecution cmd : commandExecutions) {
            System.out.println(cmd);
            switch (cmd.command) {
                case "cd":
                    fs = fs.CD(cmd.args.get(0));
                    break;
                case "ls":
                    for (String line : cmd.results) {
                        var entry = new DirEntry(fs, line);
                        fs.Children.put(entry.Name, entry);
                    }
                    break;
            }
        }

        // TODO figure out how to fix double-nested root
        root = root.Children.get("/");

        root.PrintAll();

        var sums = new HashMap<String, Integer>();
        GetDirSizes(root, sums);
        System.out.println(sums);

        var sum = 0;
        for (var entry : sums.entrySet()) {
            var s = entry.getValue();
            System.out.println(entry.getKey() + " has size " + s);
            if (s <= 100000) {
                sum += s;
            }
        }
        System.out.println(sum);
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
