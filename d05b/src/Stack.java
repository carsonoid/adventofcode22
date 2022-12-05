import java.util.ArrayList;
import java.util.List;

public class Stack {
    List<Character> crates = new ArrayList<>();

    public Stack() {
    }

    public Stack(List<Character> crate) {
        this.crates = crate;
    }

    public void Add(Character crate) {
        this.crates.add(crate);
    }

    public List<Character> Take(Integer count) {
        var taken = new ArrayList<Character>();
        for (var i = 0; i < count; i++) {
            taken.add(this.crates.remove(0));
        }
        return taken;
    }

    public void Place(List<Character> crates) {
        for (var crate : crates) {
            this.crates.add(0, crate);
        }
    }

    public void PlaceKeepOrder(List<Character> crates) {
        this.crates.addAll(0, crates);
    }

    public String toString() {
        return String.format("%s", this.crates);
    }
}
