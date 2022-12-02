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

        var MeLose = "X";
        var MeDraw = "Y";
        var MeWin = "Z";

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

            var Win = 0;
            var Draw = 1;
            var Lose = 2;

            var expectedOutcome = 0;
            var matchPoints = 0;
            if (match.me.equals(MeWin)) {
                // I win - 6 points
                System.out.println("I must win");
                matchPoints = 6;
                expectedOutcome = Win;
            } else if (match.me.equals(MeLose)) {
                // they win - no points
                System.out.println("I must Lose");
                expectedOutcome = Lose;
            } else if (match.me.equals(MeDraw)) {
                // tie - 3 points
                System.out.println("I must TIE");
                matchPoints = 3;
                expectedOutcome = Draw;
            }
            totalScore += matchPoints;

            // if expected outcome is a draw, play the same
            // so add their shape value to the total
            if (expectedOutcome == Draw) {
                totalScore += them;
                continue;
            }

            if (expectedOutcome == Win) {
                if (them == Rock) {
                    totalScore += Paper;
                }
                if (them == Paper) {
                    totalScore += Scissors;
                }
                if (them == Scissors) {
                    totalScore += Rock;
                }
            }

            if (expectedOutcome == Lose) {
                if (them == Rock) {
                    totalScore += Scissors;
                }
                if (them == Paper) {
                    totalScore += Rock;
                }
                if (them == Scissors) {
                    totalScore += Paper;
                }
            }

            System.out.printf("totalScore: %d\n", totalScore);
        }
    }
}
