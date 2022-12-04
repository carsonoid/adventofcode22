import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Input> elfPairs = allLines.stream().map(Input::FromLine).collect(Collectors.toList());

        var count = 0;
        for (Input elfPair : elfPairs) {
            System.out.println(elfPair);
            if (elfPair.HasOverlap()) {
                count++;
            }
        }
        System.out.println(count);
    }
}
