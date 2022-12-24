import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        // build rooms
        List<Room> rooms = new ArrayList<Room>();
        for (var line : lines) {
            rooms.add(new Room(line));
        }

        // add neighbors
        for (var line : lines) {
            for (var room : rooms) {
                room.AddNeighborsFromLine(line, rooms);
            }
        }

        for (var room : rooms) {
            System.out.println(room);
        }

        // find AA in rooms to start at
        Room aa = null;
        for (var room : rooms) {
            if (room.Name.equals("AA")) {
                aa = room;
            }
        }

        var walker = new RoomWalk2();
        walker.Walk(aa, rooms, new HashMap<Room, Boolean>(), 0);
    }
}

class RoomWalk2 {
    public RoomWalk2() {
    }

    public void Walk(Room current, List<Room> rooms, Map<Room, Boolean> openedRooms, int depth) {
        System.out.printf("%s== %s START\n", " ".repeat(depth), current.Name);

        openedRooms.put(current, true);

        while (openedRooms.size() != rooms.size()) {
            var result = this.findNext(current, openedRooms, new ArrayList<Room>());
            if (result.NoneFound) {
                System.out.println("No next room found");
                return;
            }

            Walk(result.Room, rooms, openedRooms, depth + 2);
        }

        System.out.printf("%s== %s END\n", " ".repeat(depth), current.Name);
    }

    private findResult findNext(Room current, Map<Room, Boolean> openedRooms, List<Room> visited) {
        visited.add(current);

        System.out.printf("== Look for next room from %s\n", current.Name);

        Room next = null;
        for (var neighbor : current.Neighbors) {
            if (openedRooms.containsKey(neighbor)) {
                continue;
            }
            if (next == null || neighbor.FlowRate > next.FlowRate) {
                next = neighbor;
            }
        }

        if (next != null) {
            return new findResult(next, false);
        }

        // if no ununopened neighbors walk down each neighbor and try again
        for (var neighbor : current.Neighbors) {
            // dont recurse on already visited rooms
            if (visited.contains(neighbor)) {
                continue;
            }

            var nestedResult = findNext(neighbor, openedRooms, visited);

            if (nestedResult.NoneFound) {
                return nestedResult;
            }

            return new findResult(next, false);
        }

        return new findResult(null, true);
    }
}

class findResult {
    public Room Room;
    public Boolean NoneFound;

    public findResult(Room room, Boolean last) {
        Room = room;
        NoneFound = last;
    }
}

class Room {
    String Name;
    Integer FlowRate;
    // Boolean Opened = false;
    List<Room> Neighbors;

    // Example line:
    // Valve AA has flow rate=0; tunnels lead to valves DD, II, BB

    public Room(String line) {
        Matcher m = Pattern
                .compile("([A-Z]{2}) has flow rate=(\\d+); .*")
                .matcher(line);
        if (m.find()) {
            Name = m.group(1);
            FlowRate = Integer.parseInt(m.group(2));
            // if (FlowRate == 0) {
            // Opened = true;
            // }
            Neighbors = new ArrayList<Room>();
        } else {
            throw new IllegalArgumentException("Invalid line: " + line);
        }
    }

    public void AddNeighborsFromLine(String line, List<Room> rooms) {
        Matcher m = Pattern
                .compile(Name + " has.*tunnels lead to valves (.*)")
                .matcher(line);
        if (m.find()) {
            String[] neighborNames = m.group(1).split(", ");
            for (var neighborName : neighborNames) {
                for (var room : rooms) {
                    if (room.Name.equals(neighborName)) {
                        Neighbors.add(room);
                    }
                }
            }
        }
    }

    public String toString() {
        List<String> neighborNames = new ArrayList<String>();
        for (var neighbor : Neighbors) {
            neighborNames.add(neighbor.Name);
        }
        return String.format("%s has flow rate=%d; tunnels lead to valves %s", Name, FlowRate,
                String.join(", ", neighborNames));
    }
}

// everything below here is modified code from the very helpful tutorial at
// https://www.baeldung.com/java-dijkstra
// I'm still no expert on pathfinding, but the instructions at that site have
// got me one step closer
class Search {

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
