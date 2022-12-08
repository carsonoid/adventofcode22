import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));

        var grid = new Grid(allLines);
        grid.Print();
        var scorer = new GridScorer(grid);
        grid.Walk(scorer);
        System.out.println(scorer.GetBest());
    }
}
