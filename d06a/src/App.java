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

            // build an array of the next 4 characters
            List<Character> charArray = new ArrayList<>();
            for (int j = i; j < i + 4; j++) {
                charArray.add(dataSet.data.get(j));
            }

            // sort for reliable distinct check
            charArray.sort(null);

            // check for unique characters
            if (charArray.stream().distinct().count() == 4) {
                System.out.println("sorted unique match: " + charArray);
                System.out.println("index: " + (i + 4));
                System.exit(0);
            }
        }
    }
}

// List<Character> charArray = new ArrayList<>();
// for (char c : dataSet.data) {
// charArray.add(c);
// if (charArray.size() == 4) {

// // duplicate charArray
// List<Character> checkArray = new ArrayList<>();
// for (char c2 : charArray) {
// checkArray.add(c2);
// }

// // sort charArray
// checkArray.sort(null);

// // check if charArray has unique elements
// if (checkArray.stream().distinct().count() == 4) {
// System.out.println("true");
// } else {
// System.out.println("false");
// }

// // remove first element to keep sliding window
// charArray.remove(0);
// }
// }
