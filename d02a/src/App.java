import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Input> matches = allLines.stream().map(Input::FromLine).collect(Collectors.toList());

        var Rock = 1;
        var Paper = 2;
        var Scissors = 3;

        var ThemRock = "A";
        var ThemPaper = "B";
        var ThemScissors = "C";

        var MeRock = "X";
        var MePaper = "Y";
        var MeScissors = "Z";

        var totalScore = 0;

        // loop over all matches and calculate the winner
        for (Input match : matches) {
            System.out.println(match);

            // convert the input to a standard format
            var them = 0;
            if (match.them.equals(ThemRock)) {
                them = Rock;
            } else if (match.them.equals(ThemPaper)) {
                them = Paper;
            } else if (match.them.equals(ThemScissors)) {
                them = Scissors;
            }

            var me = 0;
            if (match.me.equals(MeRock)) {
                me = Rock;
            } else if (match.me.equals(MePaper)) {
                me = Paper;
            } else if (match.me.equals(MeScissors)) {
                me = Scissors;
            }

            System.out.println("them: " + them);
            System.out.println("me: " + me);

            // see who won
            var matchPoints = 0;
            if (them == me) {
                // tie - 3 points
                System.out.println("TIE");
                matchPoints = 3;
            } else if ((them == Rock && me == Scissors) ||
                    (them == Paper && me == Rock) ||
                    (them == Scissors && me == Paper)) {
                // they win - no points
                System.out.println("They win");
            } else {
                // I win - 6 points
                System.out.println("I win");
                matchPoints = 6;
            }
            System.out.printf("matchPoints: %d\n", matchPoints);
            totalScore += matchPoints;

            // add the points for the shape
            totalScore += me;

            System.out.printf("totalScore: %d\n", totalScore);
        }
    }
}
