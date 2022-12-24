import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    public T Value;
    private Set<Node<T>> edges;

    public Node(T value) {
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
        return String.format("%s", this.Value);
    }
}
