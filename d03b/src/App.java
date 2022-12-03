import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Input> rucksacks = allLines.stream().map(Input::FromLine).collect(Collectors.toList());

        List<List<Input>> elfGroups = new ArrayList<>();

        List<Input> newGroup = new ArrayList<>();
        for (var i = 0; i < rucksacks.size(); i++) {
            newGroup.add(rucksacks.get(i));
            if (newGroup.size() == 3) {
                elfGroups.add(newGroup);
                newGroup = new ArrayList<>();
            }
        }

        // for each group
        var sum = 0;
        for (var group : elfGroups) {
            System.out.println("Check Group: " + group);
            var shared = group.get(0).FindShared(group.get(1), group.get(2));
            System.out.println(shared);

            // call getPriority
            sum += getPriority(shared);
        }
        System.out.println(sum);
    }

    static int getPriority(String s) {
        var ch = s.charAt(0);

        // convert char to ascii value
        int ascii = (int) ch;

        // for capital letters get the difference between the ascii value and the ascii
        // value of 'A'
        if (ascii >= 64 && ascii <= 90) {
            return ascii - (int) 'A' + 1 + 26;
        }

        // for lowercase lettersget the difference between the ascii value and the ascii
        // value of 'a'
        return ascii - (int) 'a' + 1;
    }
}
