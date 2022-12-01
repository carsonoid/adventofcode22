import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get(args[0]));
        System.out.println(allLines);

        List<Integer> AllElfCalories = new ArrayList<>();
        Integer elfCalories = 0;
        for (String line : allLines) {
            if (line.isEmpty()) {
                AllElfCalories.add(elfCalories);
                elfCalories = 0;
                continue;
            }
            elfCalories += Integer.parseInt(line);
        }

        // add the last elf calories
        AllElfCalories.add(elfCalories);

        // sort the list
        AllElfCalories.sort(Integer::compareTo);

        // print the list
        System.out.println(AllElfCalories);

        // print the last 3 elements summed
        var sum = AllElfCalories.get(AllElfCalories.size() - 1)
                + AllElfCalories.get(AllElfCalories.size() - 2) + AllElfCalories.get(AllElfCalories.size() - 3);
        System.out.println("top 3 sum: " + sum);
    }
}
