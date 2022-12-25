import java.util.ArrayList;
import java.util.List;

public class Grid implements Iterable<GridElement> {
    private List<List<Node>> grid;

    public Grid() {
        this.grid = new ArrayList<>();
    }

    public void AddRow(List<Node> value) {
        this.grid.add(value);
    }

    public Node GetNode(Integer x, Integer y) {
        var row = this.grid.get(y);
        var node = row.get(x);
        return node;
    }

    public GridElement GetAdjacent(Integer x, Integer y, Character direction) {
        var checkX = x;
        var checkY = y;
        switch (direction) {
            case 'N':
                checkY = y - 1;
                break;
            case 'S':
                checkY = y + 1;
                break;
            case 'E':
                checkX = x + 1;
                break;
            case 'W':
                checkX = x - 1;
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }
        return new GridElement(checkX, checkY, this.GetNode(checkX, checkY));
    }

    public GridElement GetAdjacentWithWrap(Integer x, Integer y, Character direction) {
        var checkX = x;
        var checkY = y;
        switch (direction) {
            case 'N':
                if (checkY == 0) {
                    checkY = this.Height() - 1;
                } else {
                    checkY = y - 1;
                }
                break;
            case 'S':
                if (checkY == this.Height() - 1) {
                    checkY = 0;
                } else {
                    checkY = y + 1;
                }
                break;
            case 'E':
                if (checkX == this.Width() - 1) {
                    checkX = 0;
                } else {
                    checkX = x + 1;
                }
                break;
            case 'W':
                if (checkX == 0) {
                    checkX = this.Width() - 1;
                } else {
                    checkX = x - 1;
                }
                break;
        }
        return new GridElement(checkX, checkY, this.GetNode(checkX, checkY));
    }

    public List<Node> TopNeighbors(Integer x, Integer y) {
        y = y - 1;
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(new Coordinate(x - 1, y));
        coords.add(new Coordinate(x, y));
        coords.add(new Coordinate(x + 1, y));
        return this.listFromCoordinates(coords);
    }

    public List<Node> BottomNeighbors(Integer x, Integer y) {
        y = y + 1;
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(new Coordinate(x - 1, y));
        coords.add(new Coordinate(x, y));
        coords.add(new Coordinate(x + 1, y));
        return this.listFromCoordinates(coords);
    }

    public List<Node> LeftNeighbors(Integer x, Integer y) {
        x = x - 1;
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(new Coordinate(x, y - 1));
        coords.add(new Coordinate(x, y));
        coords.add(new Coordinate(x, y + 1));
        return this.listFromCoordinates(coords);
    }

    public List<Node> RightNeighbors(Integer x, Integer y) {
        x = x + 1;
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(new Coordinate(x, y - 1));
        coords.add(new Coordinate(x, y));
        coords.add(new Coordinate(x, y + 1));
        return this.listFromCoordinates(coords);
    }

    private List<Node> listFromCoordinates(List<Coordinate> coords) {
        List<Node> ret = new ArrayList<Node>();

        for (Coordinate coord : coords) {
            var node = this.GetNode(coord.x, coord.y);
            if (node != null) {
                ret.add(node);
            }
        }

        return ret;
    }

    public GridIterator iterator() {
        return new GridIterator(this);
    }

    public void Print() {
        for (List<Node> row : this.grid) {
            for (Node node : row) {
                if (node == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(node.toString());
                }
            }
            System.out.println();
        }
    }

    public Integer Width() {
        return this.grid.get(0).size();
    }

    public Integer Height() {
        return this.grid.size();
    }
}

class Coordinate {
    public Integer x;
    public Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    // "(x,y)"
    public Coordinate(String s) {
        String[] parts = s.substring(1, s.length() - 1).split(",");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
