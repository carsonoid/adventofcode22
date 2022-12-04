import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input {
    int p1;
    int p2;
    int p3;
    int p4;

    public Input(int p1, int p2, int p3, int p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
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
        return String.format("%d,%d,%d,%d", p1, p2, p3, p4);
    }

    public Boolean HasOverlap() {
        if (p1 >= p3 && p2 <= p4) {
            return true;
        }

        if (p3 >= p1 && p4 <= p2) {
            return true;
        }

        return false;
    }

}
