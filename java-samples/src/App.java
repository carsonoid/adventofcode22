import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// FCF1 OMIT
interface GridWalker {
    public void HandleCell(Integer x, Integer y, String val);
}

class GridPrinter {
    public static void HandleCell(Integer x, Integer y, String val) {
        System.out.printf("%d,%d: %s", x, y, val);
    }
}

// FCF2 OMIT
class GridWalkExample {
    public static void walkGrid(List<List<String>> grid, GridWalker walker) {
        for (Integer i = 0; i < grid.size(); i++) {
            for (Integer j = 0; j < grid.get(i).size(); j++) {
                walker.HandleCell(i, j, grid.get(i).get(j));
            }
        }
    }

    public static void printGrid() {
        List<List<String>> grid = new ArrayList<>();
        grid.add(List.of("a", "b", "c"));
        grid.add(List.of("d", "e", "f"));
        grid.add(List.of("g", "h", "i"));

        walkGrid(grid, GridPrinter::HandleCell);
    }
}
// FCF3 OMIT

public class App {
    public static void main(String[] args) throws Exception {
        compareExamples();
    }

    public static void printExamples() {
        // this doesn't compile:
        // System.out.println("totalScore:", 10);

        // and this just feels ~wrong~ but does work
        System.out.println("totalScore: " + 10);

        // This feels about the same as in Go
        System.out.printf("totalScore: %d\n", 10);
    }

    public static void printlnExampleBug() {
        for (var i = 0; i < 10; i++) {
            System.out.println("round " + i + 1);
            // round 01
            // round 02
            // round 03
        }
    }

    public static void literalsBeforeJava9() {
        List<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");

        Map<String, String> data = new HashMap<>();
        data.put("Hello", "World");
        data.put("John", "Doe");

        Map<String, List<String>> groups = new HashMap<>();
        List<String> group1Users = new ArrayList<>();
        group1Users.add("user1");
        group1Users.add("user2");
        groups.put("group1", group1Users);

        List<String> group2Users = new ArrayList<>();
        group2Users.add("user3");
        group2Users.add("user4");
        groups.put("group2", group2Users);

        System.out.println(users);
        System.out.println(data);
        System.out.println(groups);
    }

    public static void listLiteralAlternatives() {
        // Most basic
        List<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");

        // After Java 8
        List<String> users1 = Arrays.asList(new String[] { "user1", "user2" });

        // After Java 9
        List<String> users2 = List.of("user1", "user2");

        // this works
        users.add("user3");

        // these would throw exceptions
        users1.add("user3");
        users2.add("user3");
    }

    public static void mapLiteralAlternatives() {
        // Most basic
        Map<String, String> data = new HashMap<>();
        data.put("Hello", "World");
        data.put("John", "Doe");

        // After Java 8
        Map<String, String> data1 = Stream.of(new String[][] {
                { "Hello", "World" },
                { "John", "Doe" },
        }).collect(Collectors.toMap(d -> d[0], d -> d[1]));

        // After Java 9
        // NOTE: supports a max of 10 k/v pairs
        Map<String, String> data2 = Map.of("Hello", "World", "John", "Doe");
    }

    public static void nestedMapListAlternatives() {
        // Most basic
        Map<String, List<String>> groups = new HashMap<>();
        List<String> group1Users = new ArrayList<>();
        group1Users.add("user1");
        group1Users.add("user2");
        groups.put("group1", group1Users);

        // After Java 9
        // NOTE: Nested lists are immutable
        Map<String, List<String>> groups2 = Map.of(
                "Hello", List.of("user1", "user2"),
                "John", List.of("user1", "user2"));
    }

    public static void sublists() {
        // NOTE: uses `substring`
        System.out.println("Hello world".substring(0, 5));

        List<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");
        users.add("user3");

        // NOTE: uses `subList`
        System.out.println(users.subList(0, 1));

        // accessors make sense on arrays
        String[] users2 = { "user4", "user5" };
        System.out.println(users2[0]);

        // but you can't slice. This would not compile:
        // System.out.println(users2[0:1]);

        // NOTE: you have to use `subList` which returns a `List`?
        users.subList(0, 1);
    }

    public static void listOps() {
        List<String> users = new ArrayList<>();
        users.add("user2"); // always cheap-ish
        users.add(0, "user1"); // could get expensive for large lists

        List<String> users2 = new LinkedList<>();
        users2.add("user3"); // always cheap
        users2.add(0, "user1"); // always cheap

        // this gets harder to know when any abstraction is added
        List<String> users3 = getUsers();

        // is this cheap, expensive?, even possible? (could be immutable!)
        users3.add(0, "user3");
    }

    public static List<String> getUsers() {
        List<String> users = new ArrayList<>();
        return users;
    }

    // START VARS OMIT
    String Name;
    Boolean IsDir;
    Integer Size;

    public static void print(String s) {
        // ...
        // END VARS OMIT
        System.out.println(s);
    }

    public static void compareExamples() {
        System.out.println("\n== compareExamples1 ==");
        compareExamples1();
        System.out.println("\n== compareExamples2 ==");
        compareExamples2();
    }

    public static void compareStrings(String X, String Y) {
        System.out.println("Constant Checking:  " + (X == "1" && Y == "1"));
        System.out.println("Pointer Comparison: " + (X == Y));
        System.out.println("Equals:             " + X.equals(Y));
    }

    public static void compareExamples1() {
        String X = "1";
        String Y = "1";

        compareStrings(X, Y);
        // Constant Checking: true
        // Pointer Comparison: true
        // Equals: true
    }

    public static void compareExamples2() {
        String X = String.valueOf(1);
        String Y = String.valueOf(1);

        compareStrings(X, Y);
        // Constant Checking: false
        // Pointer Comparison: false
        // Equals: true
    }

    public static void switchExample(String s) {
        switch (s) {
            case "1":
                System.out.println("1");
                break; // UGH!
            case "2":
                System.out.println("2");
                break; // UGH!
            case "fallthrough":
                System.out.println("fallthrough");
            default:
                System.out.println("default");
                break; // UGH!
        }
    }

    // START OVERLOAD OMIT
    public static void doThings() {
        // I like this!
        doThing("1");
        doThing(1);
    }

    public static void doThing(String x) {
        doThing(Integer.valueOf(x));
    }

    public static void doThing(Integer x) {
        System.out.println("doThing with: " + x);
    }
    // END OVERLOAD OMIT
}
