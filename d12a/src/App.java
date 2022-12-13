import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class App {
    private static Grid<WeightedNode<String>> grid;
    private static Set<WeightedNode<String>> seen = new HashSet<>();

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        grid = new Grid<WeightedNode<String>>(lines, (x, y, val) -> new WeightedNode<String>(x, y, val));

        WeightedNode<String> start = null;
        for (var cell : grid) {
            if (cell.Value.equals("S")) {
                start = cell;
            }
        }
        start.Value = "a"; // height is actually `a`
        System.out.println(start);

        buildGraph(start);

        calculateShortestPathFromSource(start);

        for (var cell : grid) {
            if (cell.Value.equals("E")) {
                System.out.println(cell.distance);
            }
        }
    }

    public static void buildGraph(WeightedNode<String> current) {
        if (seen.contains(current)) {
            return;
        }
        seen.add(current);
        for (var n : grid.getCardinalNeighbors(current.X, current.Y)) {
            // filter out neighbors that are too high
            int charValue = current.Value.charAt(0);
            var h = String.valueOf((char) (charValue + 1));
            int maxHeight = h.charAt(0);
            int neighborHeight = n.Value.charAt(0);
            // avoid 'E' being > 'z' in by replacing it with 'z'+1
            if (neighborHeight == 'E') {
                neighborHeight = 'z' + 1;
            }

            if (maxHeight >= neighborHeight) {
                var weight = maxHeight - neighborHeight;
                System.out.printf("%s -> %s - %d\n", current, n, weight);
                if (!current.HasEdge(n)) {
                    current.AddEdge(n, weight);
                    buildGraph(n);
                }
            }
        }
    }

    // everything below here is modified code from the very helpful tutorial at
    // https://www.baeldung.com/java-dijkstra
    // I'm still no expert on pathfinding, but the instructions at that site have
    // got me one step closer

    public static void calculateShortestPathFromSource(WeightedNode<String> source) {
        source.distance = 0;

        Set<WeightedNode<String>> settledNodes = new HashSet<>();
        Set<WeightedNode<String>> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            WeightedNode<String> currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<WeightedNode<String>, Integer> adjacencyPair : currentNode.adjacentNodes.entrySet()) {
                WeightedNode<String> adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static void CalculateMinimumDistance(WeightedNode<String> evaluationNode,
            Integer edgeWeigh, WeightedNode<String> sourceNode) {
        Integer sourceDistance = sourceNode.distance;
        if (sourceDistance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + edgeWeigh;
            LinkedList<WeightedNode<String>> shortestPath = new LinkedList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }

    private static WeightedNode<String> getLowestDistanceNode(Set<WeightedNode<String>> unsettledNodes) {
        WeightedNode<String> lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (WeightedNode<String> node : unsettledNodes) {
            int nodeDistance = node.distance;
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}
