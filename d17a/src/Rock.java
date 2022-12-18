import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rock {
    CharCell[][] cells;

    // chamber is 7 wide so rocks can't go past 6
    public static final int MAX_X = 6;

    public Rock(String[] lines) {
        List<String> list = new ArrayList<>();
        for (var line : lines) {
            list.add(line);
        }
        this.setCells(list);
    }

    public Rock(List<String> lines) {
        this.setCells(lines);
    }

    private void setCells(List<String> lines) {
        Collections.reverse(lines);

        this.cells = new CharCell[lines.size()][lines.get(0).length()];

        for (var y = 0; y < lines.size(); y++) {
            var row = new CharCell[lines.get(y).length()];
            var chars = lines.get(y).toCharArray();
            for (var x = 0; x < chars.length; x++) {
                var cell = new CharCell(chars[x]);
                cell.setCoord(x, y);
                row[x] = cell;
            }
            this.cells[y] = row;
        }
    }

    public List<CharCell> LeftEdge() {
        var leftEdge = new ArrayList<CharCell>();

        for (var y = 0; y < this.cells.length; y++) {
            var row = this.cells[y];
            for (var x = 0; x < row.length; x++) {
                var cell = row[x];

                if (cell.value == '.') {
                    continue;
                }

                if (x == 0) {
                    // cell.value = 'L';
                    leftEdge.add(cell);
                }

                var leftX = x - 1;
                if (leftX < 0) {
                    continue;
                }
                var left = this.cells[y][leftX];
                if (left.value == '.') {
                    // cell.value = 'L';
                    leftEdge.add(cell);
                }
                break;
            }
        }
        return leftEdge;
    }

    public List<CharCell> RightEdge() {
        var rightEdge = new ArrayList<CharCell>();

        for (var y = 0; y < this.cells.length; y++) {
            var row = this.cells[y];
            for (var x = 0; x < row.length; x++) {
                var cell = row[x];

                if (cell.value == '.') {
                    continue;
                }

                if (x == row.length - 1) {
                    // cell.value = 'R';
                    rightEdge.add(cell);
                }

                var rightX = x + 1;
                if (rightX == row.length) {
                    continue;
                }
                var left = this.cells[y][rightX];
                if (left.value == '.') {
                    // cell.value = 'R';
                    rightEdge.add(cell);
                }
            }
        }
        return rightEdge;
    }

    // the bottom edge is every cell that doesn't have a cell below it
    public List<CharCell> BottomEdge() {
        var edge = new ArrayList<CharCell>();

        // because we are storing the cells in reverse order, the bottom edge is the
        // first row
        for (var y = 0; y < this.cells.length; y++) {
            var row = this.cells[y];
            for (var x = 0; x < row.length; x++) {
                var cell = row[x];

                if (cell.value == '.') {
                    continue;
                }

                if (y == 0) {
                    // cell.value = 'B';
                    edge.add(cell);
                    continue;
                }

                var belowY = y + 1;
                if (belowY == this.cells.length) {
                    continue;
                }
                var below = this.cells[belowY][x];
                if (below.value == '.') {
                    // cell.value = 'B';
                    edge.add(cell);
                }
            }
        }
        return edge;

    }

    public List<CharCell> TopEdge() {
        var edge = new ArrayList<CharCell>();

        // because we are storing the cells in reverse order, the top edge is the
        // last row
        for (var y = this.cells.length - 1; y >= 0; y--) {
            var row = this.cells[y];
            for (var x = 0; x < row.length; x++) {
                var cell = row[x];

                if (cell.value == '.') {
                    continue;
                }

                if (y == this.cells.length - 1) {
                    // cell.value = 'T';
                    edge.add(cell);
                    continue;
                }

                var aboveY = y - 1;
                if (aboveY == this.cells.length) {
                    continue;
                }

                if (aboveY < 0) {
                    continue;
                }

                var below = this.cells[aboveY][x];
                if (below.value == '.') {
                    // cell.value = 'T';
                    edge.add(cell);
                }

            }
        }
        return edge;
    }

    public int maxHeight() {
        for (var y = this.cells.length - 1; y >= 0; y--) {
            var row = this.cells[y];
            for (var x = 0; x < row.length; x++) {
                var cell = row[x];

                if (cell.value == '.') {
                    continue;
                }

                // add 1 because the y coordinate is zero based
                return cell.Y + 1;
            }
        }
        throw new RuntimeException("no cells found");
    }

    public void SetTo(int leftEdgePos, int bottomEdgePos) {
        for (var y = 0; y < this.cells.length; y++) {
            for (var x = 0; x < this.cells[y].length; x++) {
                var cell = this.cells[y][x];
                cell.X = leftEdgePos + x;
                cell.Y = bottomEdgePos + y;
            }
        }
    }

    public boolean Move(char dir, Chamber chamber) {
        switch (dir) {
            case '>':
                return this.MoveRight(chamber);
            case '<':
                return this.MoveLeft(chamber);
            case 'v':
                return this.MoveDown(chamber);
            default:
                throw new RuntimeException("Invalid direction");
        }
    }

    public boolean MoveRight(Chamber chamber) {
        // Check if we can move right
        for (var cell : this.RightEdge()) {
            if (cell.X == MAX_X) {
                return false;
            }

            if (cell.Y >= chamber.Height()) {
                continue;
            }

            // only check cells that are within the chamber
            var moveX = cell.X + 1;
            if (moveX <= MAX_X) {
                var check = chamber.GetCell(moveX, cell.Y);
                if (check.value != '.') {
                    return false;
                }
            }
        }

        // move all cells right
        for (var row : this.cells) {
            for (var cell : row) {
                cell.X++;
            }
        }
        return true;
    }

    public boolean MoveLeft(Chamber chamber) {
        // Check if we can move right
        for (var cell : this.LeftEdge()) {
            if (cell.X == 0) {
                return false;
            }

            if (cell.Y >= chamber.Height()) {
                continue;
            }

            // only check cells that are within the chamber
            var moveX = cell.X - 1;
            if (moveX >= 0) {
                var check = chamber.GetCell(moveX, cell.Y);
                if (check.value != '.') {
                    return false;
                }
            }
        }

        // move all cells right
        for (var row : this.cells) {
            for (var cell : row) {
                cell.X--;
            }
        }

        return true;
    }

    public boolean MoveDown(Chamber chamber) {
        if (this.CanGoDown(chamber)) {
            return false;
        }

        // move all cells down
        for (var row : this.cells) {
            for (var cell : row) {
                cell.Y--;
            }
        }
        return true;
    }

    // CanGoDown returns true if any of the bottom cells are at the bottom of the
    // chamber or if any of the cells below them are not empty
    public boolean CanGoDown(Chamber chamber) {
        for (var cell : this.BottomEdge()) {
            // if we are at the bottom of the chamber we can't fall
            if (cell.Y == 0) {
                return true;
            }

            // all cells below us must be empty
            //
            // NOTE: we don't have to check for our own cells because
            // we aren't written to the chamber yet
            var fallTo = cell.Y - 1;

            // only check cells that are within the chamber
            if (fallTo <= chamber.Height() - 1) {
                var below = chamber.GetCell(cell.X, fallTo);
                if (below.value != '.') {
                    return true;
                }
            }
        }

        return false;
    }

    // public String toString() {
    // return String.valueOf(this.jets);
    // }
}
