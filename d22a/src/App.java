import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // figure out the max width
        var maxWidth = 0;
        for (var line : lines) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
            if (line.isEmpty()) {
                break;
            }
        }

        Grid grid = new Grid();

        for (int y = 0; y < lines.size(); y++) {
            if (y == lines.size() - 1) {
                break;
            }
            String line = lines.get(y);
            List<Node> row = new ArrayList<>();
            for (int x = 0; x < line.length(); x++) {
                var c = line.charAt(x);
                row.add(new Node(c));
            }

            if (row.size() > 0) {
                if (row.size() < maxWidth) {
                    for (int i = row.size(); i < maxWidth; i++) {
                        row.add(new Node(' '));
                    }
                }
                grid.AddRow(row);
            }
        }

        // grid.Print();

        var last = lines.get(lines.size() - 1);

        List<Instruction> moves = new ArrayList<>();
        var dist = "";
        for (int i = 0; i < last.length(); i++) {
            var c = last.charAt(i);
            if (c == 'L' || c == 'R') {
                if (dist.length() > 0) {
                    moves.add(new Instruction(Integer.parseInt(dist)));
                }

                moves.add(new Instruction(c));
                dist = "";
            } else {
                dist += c;
            }
        }
        if (dist.length() > 0) {
            moves.add(new Instruction(Integer.parseInt(dist)));
        }

        System.out.println(moves);

        GridElement current = null;
        for (var elem : grid) {
            if (elem.value.Value == '.') {
                current = elem;
                break;
            }
        }
        System.out.println("Start at " + current);

        var facing = 'E';

        // follow instructions
        List<Character> PossibleDirections = new ArrayList<>();
        PossibleDirections.add('N');
        PossibleDirections.add('E');
        PossibleDirections.add('S');
        PossibleDirections.add('W');

        for (var move : moves) {
            if (move.Type == InstructionType.TURN) {
                if (move.Direction == 'R') {
                    facing = PossibleDirections.get((PossibleDirections.indexOf(facing) + 1) % 4);
                } else {
                    facing = PossibleDirections.get((PossibleDirections.indexOf(facing) + 3) % 4);
                }
                System.out.println("Turning " + move.Direction + " to face " + facing);
                continue;
            }

            System.out.println("Moving " + move.Distance + " in direction " + facing);
            var possible = current;
            for (int numMoved = 0; numMoved < move.Distance;) {
                var next = grid.GetAdjacentWithWrap(possible.x, possible.y, facing);

                if (next.value.Value == '.') {
                    System.out.println("Moving " + facing + " from " + possible + " to " + next);
                    current = possible = next;
                    numMoved++;
                    continue;
                }

                if (next.value.Value == '#') {
                    System.out.println("Hit a wall at " + next);
                    break;
                }

                if (next.value.Value == ' ') {
                    // keep going around but don't count it as a move
                    // System.out.println("EMPTY at " + next);
                    possible = next;
                }
            }
        }
        System.out.println("End at " + current);

        // calc needs 1 based index
        var col = current.x + 1;
        var row = current.y + 1;
        var facingNum = 0;
        switch (facing) {
            case 'N':
                facingNum = 3;
                break;
            case 'E':
                facingNum = 0;
                break;
            case 'S':
                facingNum = 1;
                break;
            case 'W':
                facingNum = 2;
                break;
        }
        System.out.println("row: " + row + " col: " + col + " facing: " + facingNum);
        var result = 1000 * row + 4 * col + facingNum;
        System.out.println("Result: " + result);
    }
}

class Instruction {
    InstructionType Type;

    Integer Distance;
    Character Direction;

    public Instruction() {
    }

    public Instruction(Integer distance) {
        this.Type = InstructionType.MOVE;
        this.Distance = distance;
    }

    public Instruction(Character direction) {
        this.Type = InstructionType.TURN;
        this.Direction = direction;
    }

    public String toString() {
        if (this.Type == InstructionType.TURN) {
            return "Turn " + this.Direction.toString();
        }
        return "Move " + this.Distance.toString();
    }
}

enum InstructionType {
    TURN,
    MOVE,
}
