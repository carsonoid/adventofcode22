import java.util.HashMap;
import java.util.Map;

public class RopePoint {
    int x;
    int y;
    RopePoint parent;
    RopePoint child;
    Map<String, Boolean> visited;

    public RopePoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = new HashMap<>();
        this.visited.put(this.toString(), true);
    }

    public void AddChild(RopePoint child) {
        this.child = child;
        child.parent = this;
    }

    public void Move(Character dir) {
        switch (dir) {
            // cardinals
            case 'U':
                this.y++;
                break;
            case 'D':
                this.y--;
                break;
            case 'L':
                this.x--;
                break;
            case 'R':
                this.x++;
                break;
            default:
                throw new RuntimeException("unknown direction");
        }

        if (this.hasChild()) {
            this.child.Follow();
        }
    }

    public void Move(MoveInstruction move) {
        for (var i = move.dist; i > 0; i--) {
            this.Move(move.dir);
        }
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public boolean hasChild() {
        return this.child != null;
    }

    public void Follow() {
        var dx = this.parent.x - this.x;
        var dy = this.parent.y - this.y;

        if (dx == 0 && dy == -2) {
            this.y--;
        } else if (dx == 0 && dy == 2) {
            this.y++;
        } else if (dx == -2 && dy == 0) {
            this.x--;
        } else if (dx == 2 && dy == 0) {
            this.x++;
        } else if (dx == 2 && dy == 1) {
            this.x++;
            this.y++;
        } else if (dx == 2 && dy == -1) {
            this.x++;
            this.y--;
        } else if (dx == -2 && dy == 1) {
            this.x--;
            this.y++;
        } else if (dx == -2 && dy == -1) {
            this.x--;
            this.y--;
        } else if (dx == 1 && dy == 2) {
            this.x++;
            this.y++;
        } else if (dx == 1 && dy == -2) {
            this.x++;
            this.y--;
        } else if (dx == -1 && dy == 2) {
            this.x--;
            this.y++;
        } else if (dx == -1 && dy == -2) {
            this.x--;
            this.y--;
        } else if (dx == -2 && dy == -2) {
            this.x--;
            this.y--;
        } else if (dx == 2 && dy == 2) {
            this.x++;
            this.y++;
        } else if (dx == -2 && dy == 2) {
            this.x--;
            this.y++;
        } else if (dx == 2 && dy == -2) {
            this.x++;
            this.y--;
        }

        // even if we didn't move, this is fine
        this.visited.put(this.toString(), true);

        if (this.hasChild()) {
            this.child.Follow();
        }
    }

    public String toString() {
        return String.format("%d,%d", x, y);
    }

    public void Print() {
        System.out.println(this);
        if (this.hasChild()) {
            this.child.Print();
        }
    }
}
