import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input {
    int a1;
    int a2;
    int b1;
    int b2;

    public Input(int p1, int p2, int p3, int p4) {
        this.a1 = p1;
        this.a2 = p2;
        this.b1 = p3;
        this.b2 = p4;
    }

    public static Input FromLine(String input) {
        var parts = input.split(",");
        var parts1 = parts[0].split("-");
        var p1 = Integer.parseInt(parts1[0]);
        var p2 = Integer.parseInt(parts1[1]);

        var parts2 = parts[1].split("-");
        var p3 = Integer.parseInt(parts2[0]);
        var p4 = Integer.parseInt(parts2[1]);

        return new Input(p1, p2, p3, p4);
    }

    public String toString() {
        return String.format("%d,%d,%d,%d", a1, a2, b1, b2);
    }

    public Boolean HasOverlap() {
        if (a1 >= b1 && a1 <= b2) {
            return true;
        }

        if (a2 >= b1 && a2 <= b2) {
            return true;
        }

        if (b1 >= a1 && b1 <= a2) {
            return true;
        }

        if (b2 >= a1 && b2 <= a2) {
            return true;
        }

        return false;
    }

}
