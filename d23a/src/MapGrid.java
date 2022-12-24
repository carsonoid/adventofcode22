import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGrid implements Iterable<GridElement> {
    // Map<Y, Map<X, Node<T>>>
    private Map<Integer, Map<Integer, Node>> grid;

    public Integer xmin = 0;
    public Integer xmax = 0;
    public Integer ymin = 0;
    public Integer ymax = 0;

    public MapGrid() {
        this.grid = new HashMap<>();
    }

    public void AddNode(Integer x, Integer y, Node value) {
        if (!this.grid.containsKey(y)) {
            this.grid.put(y, new HashMap<>());
        }
        this.grid.get(y).put(x, value);
        this.Expand(x, y);
    }

    private void Expand(Integer x, Integer y) {
        if (x < this.xmin) {
            this.xmin = x;
        }
        if (x > this.xmax) {
            this.xmax = x;
        }
        if (y < this.ymin) {
            this.ymin = y;
        }
        if (y > this.ymax) {
            this.ymax = y;
        }
    }

    public Node GetNode(Integer x, Integer y) {
        if (!this.grid.containsKey(y)) {
            return null;
        }
        return this.grid.get(y).get(x);
    }

    public void RemoveNode(Integer x, Integer y) {
        if (!this.grid.containsKey(y)) {
            return;
        }
        this.grid.get(y).remove(x);
    }

    public Node Move(Integer x, Integer y, Integer dx, Integer dy) {
        var node = this.GetNode(x, y);
        if (node == null) {
            throw new RuntimeException("No node at " + x + "," + y);
        }
        this.RemoveNode(x, y);
        this.AddNode(dx, dy, node);
        return node;
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

    public Coordinate ProposeMove(Integer x, Integer y, List<Character> checkOrder) {
        var top = this.TopNeighbors(x, y);
        var bottom = this.BottomNeighbors(x, y);
        var left = this.LeftNeighbors(x, y);
        var right = this.RightNeighbors(x, y);

        // no neighbors means no move
        if (top.isEmpty() && bottom.isEmpty() && left.isEmpty() && right.isEmpty()) {
            return null;
        }

        for (var c : checkOrder) {
            switch (c) {
                case 'N':
                    if (top.isEmpty()) {
                        // System.out.println('N');
                        return new Coordinate(x, y - 1);
                    }
                    break;
                case 'S':
                    if (bottom.isEmpty()) {
                        // System.out.println('S');
                        return new Coordinate(x, y + 1);
                    }
                    break;
                case 'W':
                    if (left.isEmpty()) {
                        // System.out.println('W');
                        return new Coordinate(x - 1, y);
                    }
                    break;
                case 'E':
                    if (right.isEmpty()) {
                        // System.out.println('E');
                        return new Coordinate(x + 1, y);
                    }
                    break;
            }
        }

        return null;
    }

    public GridIterator iterator() {
        return new GridIterator(this);
    }

    public void Print() {
        for (int y = this.ymin; y <= this.ymax; y++) {
            for (int x = this.xmin; x <= this.xmax; x++) {
                Node node = this.GetNode(x, y);
                if (node == null) {
                    System.out.print(".");
                } else {
                    System.out.print(node);
                }
            }
            System.out.println();
        }
    }

    public Integer GetHeight() {
        if (this.ymin >= 0) {
            return this.ymax - this.ymin + 1;
        }
        return Math.abs(this.ymin) + Math.abs(this.ymax) + 1;
    }

    public Integer GetWidth() {
        if (this.xmin >= 0) {
            return this.xmax - this.xmin + 1;
        }
        return Math.abs(this.xmin) + Math.abs(this.xmax) + 1;
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
