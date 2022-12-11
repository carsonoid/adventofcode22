import java.util.ArrayList;
import java.util.List;

public class Monkey {
    List<Integer> items;
    String Operation;
    Integer testDivisor;
    Integer trueDest;
    Integer falseDest;

    Integer inspectionCount = 0;

    public Monkey(List<String> description) {
        this.items = new ArrayList<>();
        for (var item : description.get(1).split(": ")[1].split(", ")) {
            items.add(Integer.parseInt(item));
        }

        this.Operation = description.get(2).split("new = ")[1];

        this.testDivisor = Integer.parseInt(description.get(3).split("divisible by ")[1]);
        this.trueDest = Integer.parseInt(description.get(4).split("throw to monkey ")[1]);
        this.falseDest = Integer.parseInt(description.get(5).split("throw to monkey ")[1]);
    }

    public Integer InspectNext(Long commonDivisor) {
        if (this.items.size() == 0) {
            return -1;
        }
        this.inspectionCount++;

        var item = this.items.remove(0);
        var op = this.Operation.replaceAll("old", item.toString());

        Long next = Long.valueOf("1");
        var parts = op.split(" ");
        var x = Long.parseLong(parts[0]);
        var y = Long.parseLong(parts[2]);
        switch (parts[1]) {
            case "+":
                next = x + y;
                break;
            case "*":
                next = x * y;
                break;
        }

        next = next % commonDivisor;

        return Integer.parseInt(next.toString());
    }
}
