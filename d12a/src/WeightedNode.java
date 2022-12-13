import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WeightedNode<T> extends GridCell {
    public T Value;
    public int distance = Integer.MAX_VALUE;
    public LinkedList<WeightedNode<T>> shortestPath = new LinkedList<>();

    Map<WeightedNode<T>, Integer> adjacentNodes = new HashMap<>();

    public WeightedNode(int X, int Y, T value) {
        super(X, Y);

        this.Value = value;
        this.adjacentNodes = new HashMap<>();
    }

    public boolean HasEdge(WeightedNode<T> n) {
        return this.adjacentNodes.containsKey(n);
    }

    public void AddEdge(WeightedNode<T> n, Integer dist) {
        this.adjacentNodes.put(n, 1);
    }

    // public Set<WeightedNode<T>> GetEdges() {
    // return this.adjacentNodes;
    // }

    public String toString() {
        return String.format("(%d,%d) %s", this.X, this.Y, this.Value);
    }
}
