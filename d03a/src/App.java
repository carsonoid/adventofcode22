import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Input> rucksacks = allLines.stream().map(Input::FromLine).collect(Collectors.toList());

        int sum = 0;
        for (Input rucksack : rucksacks) {
            System.out.println(rucksack);

            var dup = rucksack.FindDuplicates();
            char ch = dup.toCharArray()[0];

            // convert char to ascii value
            int ascii = (int) ch;

            int val;
            // for capital letters get the difference between the ascii value and the ascii
            // value of 'A'
            if (ascii >= 64 && ascii <= 90) {
                val = ascii - (int) 'A' + 1 + 26;
            } else {
                // for lowercase lettersget the difference between the ascii value and the ascii
                // value of 'a'
                val = ascii - (int) 'a' + 1;
            }

            System.out.println("dup is " + dup + " worth " + val);

            sum += val;
        }

        System.out.println("sum is " + sum);
    }
}
