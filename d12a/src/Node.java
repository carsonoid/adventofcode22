import java.util.HashSet;
import java.util.Set;

public class Node<T> extends GridCell {
    public T Value;
    private Set<Node<T>> edges;

    public Node(int X, int Y, T value) {
        super(X, Y);

        this.Value = value;
        this.edges = new HashSet<>();
    }

    public boolean HasEdge(Node<T> n) {
        return this.edges.contains(n);
    }

    public void AddEdge(Node<T> n) {
        this.edges.add(n);
    }

    public Set<Node<T>> GetEdges() {
        return this.edges;
    }

    public String toString() {
        // return String.format("(%d,%d) %s has edges: %s", this.X, this.Y, this.value,
        // this.edges);
        return String.format("(%d,%d) %s", this.X, this.Y, this.Value);
    }
}
