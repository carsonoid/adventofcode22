import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
    private static final int ArrayList = 0;

    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Input> dataSets = allLines.stream().map(Input::FromLine).collect(Collectors.toList());

        var dataSet = dataSets.get(0);
        System.out.println(dataSet);

        // go through each position in the string
        for (int i = 0; i < dataSet.data.size(); i++) {

            // build an array of the next 14 characters
            List<Character> charArray = new ArrayList<>();
            for (int j = i; j < i + 14; j++) {
                charArray.add(dataSet.data.get(j));
            }

            // sort for reliable distinct check
            charArray.sort(null);

            // check for unique characters
            if (charArray.stream().distinct().count() == 14) {
                System.out.println("sorted unique match: " + charArray);
                System.out.println("index: " + (i + 14));
                System.exit(0);
            }
        }
    }
}
