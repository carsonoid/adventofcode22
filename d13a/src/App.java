import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        List<PacketPair> pairs = new ArrayList<>();

        for (var i = 0; i < lines.size(); i += 3) {
            pairs.add(new PacketPair(lines.get(i), lines.get(i + 1)));
        }

        // System.out.println(pairs);

        var sum = 0;
        for (var i = 0; i < pairs.size(); i++) {
            var left = new Packet(pairs.get(i).Left);
            var right = new Packet(pairs.get(i).Right);
            System.out.print(i);
            var cmp = compare(left.Data, right.Data);
            switch (cmp) {
                case 1:
                    System.out.println(" is in order");
                    sum += i + 1;
                    break;
                case -1:
                    System.out.println(" is NOT in order");
                    break;
                default:
                    throw new RuntimeException("Expected non-equal result");
            }
        }
        System.out.println(sum);
    }

    private static int compare(List<PacketEntry> a, List<PacketEntry> b) {
        var i = 0;

        while (true) {
            if (i == a.size() && i == b.size()) {
                return 0;
            }
            if (i > a.size() - 1) {
                return 1;
            }
            if (i > b.size() - 1) {
                return -1;
            }

            var left = a.get(i);
            var right = b.get(i);

            i++;

            if (!left.IsPacket && !right.IsPacket) {
                // System.out.printf("compare %d vs %d\n", left.data, right.data);
                if (left.data < right.data) {
                    return 1;
                } else if (left.data.equals(right.data)) {
                    continue;
                } else {
                    return -1;
                }
            } else if (left.IsPacket && right.IsPacket) {
                // System.out.printf("compare packets %s vs %s\n", left.packet, right.packet);
                var cmp = compare(left.packet.Data, right.packet.Data);
                if (cmp != 0) {
                    return cmp;
                }
            } else if (left.IsPacket && !right.IsPacket) {
                var rightData = new ArrayList<PacketEntry>();
                rightData.add(new PacketEntry(right.data));
                var cmp = compare(left.packet.Data, rightData);
                if (cmp != 0) {
                    return cmp;
                }
            } else if (!left.IsPacket && right.IsPacket) {
                var leftData = new ArrayList<PacketEntry>();
                leftData.add(new PacketEntry(left.data));
                var cmp = compare(leftData, right.packet.Data);
                if (cmp != 0) {
                    return cmp;
                }
            } else {
                throw new RuntimeException("unexpected state");
            }
        }
    }
}
